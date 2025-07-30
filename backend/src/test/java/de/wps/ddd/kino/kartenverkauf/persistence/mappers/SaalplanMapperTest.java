package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.persistence.model.SaalplanEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaalplanMapperTest {

    SaalplanMapper saalplanMapper = new SaalplanMapperImpl();

    private final int saalplanId = 2;
    private final PlatzNummer platzNr = new PlatzNummer(1);
    private final ReiheNummer reiheNr = new ReiheNummer(42);
    private final boolean istVerkauft = false;
    private final String reservierungsnummerString = "reservierungsnummer";
    private final Reservierungsnummer reservierungsnummer = new Reservierungsnummer(reservierungsnummerString);
    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74"));
    private final PlatzEntity platzEntity = new PlatzEntity(new PlatzEntity.Id(saalplanId, reiheNr.nummer(), platzNr.nummer()), istVerkauft, reservierungsnummerString);
    private final List<PlatzEntity> platzEntities = List.of(platzEntity);

    @BeforeEach
    public void setup() {
        saalplanMapper.setPlatzMapper(new PlatzMapperImpl());
    }

    @Test
    public void saalplanToSaalplanEntity() {
        // arrange
        var plaetze = List.of(new Platz(new PlatzId(reiheNr, platzNr), istVerkauft, reservierungsnummer));
        var saalplan = new Saalplan(vorstellungId, plaetze);

        // act
        var saalplanEntity = saalplanMapper.saalplanToSaalplanEntity(saalplan, saalplanId);

        // assert
        assertThat(saalplanEntity.getId()).isEqualTo(saalplanId);
        assertThat(saalplanEntity.getVorstellungUUID()).isEqualTo(vorstellungId.uuid());
        assertThat(saalplanEntity.getPlaetze()).isEqualTo(platzEntities);
    }

    @Test
    public void saalplanEntityToSaalplan() {
        // arrange
        var saalplanEntity = new SaalplanEntity(saalplanId, vorstellungId.uuid(), platzEntities);

        // act
        var saalplan = saalplanMapper.saalplanEntityToSaalplan(saalplanEntity);

        // assert
        assertThat(saalplan.getVorstellungId()).isEqualTo(vorstellungId);
        Platz mappedPlatz = saalplan.getPlaetze().get(reiheNr).get(platzNr);
        assertThat(mappedPlatz.getPlatzId().reiheNr()).isEqualTo(reiheNr);
        assertThat(mappedPlatz.getPlatzId().platzNr()).isEqualTo(platzNr);
        assertThat(mappedPlatz.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(mappedPlatz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
    }
}