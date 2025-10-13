package de.wps.ddd.kino.kartenverkauf.adapters.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.SaalplanEntityMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.SaalplanEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaalplanEntityMapperTest {

    private final SaalplanEntityMapper mapper = new SaalplanEntityMapper();

    private final int saalplanId = 2;
    private final PlatzNummer platzNr = new PlatzNummer(1);
    private final ReiheNummer reiheNr = new ReiheNummer(42);
    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74"));

    @Test
    public void toEntity() {
        // arrange
        var plaetze = List.of(new Platz(new PlatzId(reiheNr, platzNr), false));
        var saalplan = new Saalplan(vorstellungId, plaetze);

        // act
        var saalplanEntity = mapper.toEntity(saalplan, saalplanId);

        // assert
        assertThat(saalplanEntity.getId()).isEqualTo(saalplanId);
        assertThat(saalplanEntity.getVorstellungUUID()).isEqualTo(vorstellungId.uuid());
        assertThat(saalplanEntity.getPlaetze()).hasSize(1);

        var platzEntity = saalplanEntity.getPlaetze().getFirst();
        assertThat(platzEntity.getId().getSaalplanId()).isEqualTo(saalplanId);
        assertThat(platzEntity.getId().getReihe()).isEqualTo(reiheNr.nummer());
        assertThat(platzEntity.getId().getPlatz()).isEqualTo(platzNr.nummer());
        assertThat(platzEntity.isIstVerkauft()).isFalse();
    }

    @Test
    public void toDomain() {
        // arrange
        var saalplanEntity = new SaalplanEntity(saalplanId, vorstellungId.uuid());
        saalplanEntity.addPlatz(reiheNr.nummer(), platzNr.nummer(), false);

        // act
        var saalplan = mapper.toDomain(saalplanEntity);

        // assert
        assertThat(saalplan.getVorstellungId()).isEqualTo(vorstellungId);
        var platz = saalplan.platz(new PlatzId(reiheNr, platzNr));
        assertThat(platz.getId().reihe()).isEqualTo(reiheNr);
        assertThat(platz.getId().platz()).isEqualTo(platzNr);
        assertThat(platz.isIstVerkauft()).isFalse();
    }
    
}