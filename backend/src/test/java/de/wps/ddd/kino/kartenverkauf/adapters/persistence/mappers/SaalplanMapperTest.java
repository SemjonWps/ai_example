package de.wps.ddd.kino.kartenverkauf.adapters.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.adapters.persistence.model.SaalplanEntity;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaalplanMapperTest {

    private final SaalplanMapper mapper = new SaalplanMapperImpl();

    private final int saalplanId = 2;
    private final PlatzNummer platzNr = new PlatzNummer(1);
    private final ReiheNummer reiheNr = new ReiheNummer(42);
    private final boolean istVerkauft = false;
    private final String reservierungsnummerString = "reservierungsnummer";
    private final Reservierungsnummer reservierungsnummer = new Reservierungsnummer(reservierungsnummerString);
    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74"));
    private final PlatzEntity platzEntity = new PlatzEntity(new PlatzEntity.Id(saalplanId, reiheNr.nummer(), platzNr.nummer()), istVerkauft, reservierungsnummerString);
    private final List<PlatzEntity> platzEntities = List.of(platzEntity);

    @Test
    public void saalplanToSaalplanEntity() {
        // arrange
        var plaetze = List.of(new Platz(new PlatzId(reiheNr, platzNr), istVerkauft, reservierungsnummer));
        var saalplan = new Saalplan(vorstellungId, plaetze);

        // act
        var saalplanEntity = mapper.saalplanToSaalplanEntity(saalplan, saalplanId);

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
        var saalplan = mapper.saalplanEntityToSaalplan(saalplanEntity);

        // assert
        assertThat(saalplan.getVorstellungId()).isEqualTo(vorstellungId);
        Platz mappedPlatz = saalplan.getPlaetze().get(reiheNr).get(platzNr);
        assertThat(mappedPlatz.getId().reihe()).isEqualTo(reiheNr);
        assertThat(mappedPlatz.getId().platz()).isEqualTo(platzNr);
        assertThat(mappedPlatz.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(mappedPlatz.getReservierung()).isEqualTo(reservierungsnummer);
    }

    @Test
    public void testPlatzEntityToPlatz() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(new PlatzEntity.Id(saalplanId, reiheNr.nummer(), platzNr.nummer()), false, reservierungsnummer.nummer());

        // act
        Platz platz = mapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getId().reihe().nummer()).isEqualTo(reiheNr.nummer());
        assertThat(platz.getId().platz().nummer()).isEqualTo(platzNr.nummer());
        assertThat(platz.isIstVerkauft()).isFalse();
        assertThat(platz.getReservierung()).isEqualTo(reservierungsnummer);
    }

    @Test
    public void testPlatzEntityToPlatz_reservierungsnummer_null() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(new PlatzEntity.Id(saalplanId, reiheNr.nummer(), platzNr.nummer()), true, null);

        // act
        Platz platz = mapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getId().reihe().nummer()).isEqualTo(reiheNr.nummer());
        assertThat(platz.getId().platz().nummer()).isEqualTo(platzNr.nummer());
        assertThat(platz.isIstVerkauft()).isTrue();
        assertThat(platz.getReservierung()).isNull();
    }

    @Test
    public void testPlatzToPlatzEntity() {
        // arrange
        Platz platz = new Platz(new PlatzId(reiheNr, platzNr), false, reservierungsnummer);

        // act
        PlatzEntity platzEntity = mapper.platzToPlatzEntity(platz, saalplanId);

        // assert
        assertThat(platzEntity.getId().getSaalplanId()).isEqualTo(saalplanId);
        assertThat(platzEntity.getId().getReihe()).isEqualTo(reiheNr.nummer());
        assertThat(platzEntity.getId().getPlatz()).isEqualTo(platzNr.nummer());
        assertThat(platzEntity.isIstVerkauft()).isFalse();
        assertThat(platzEntity.getReservierung()).isEqualTo(reservierungsnummer.nummer());
    }

    @Test
    public void testPlatzToPlatzEntity_reservierungsnummer_null() {
        // arrange
        Platz platz = new Platz(new PlatzId(reiheNr, platzNr), true, null);

        // act
        PlatzEntity platzEntity = mapper.platzToPlatzEntity(platz, saalplanId);

        // assert
        assertThat(platzEntity.getId().getSaalplanId()).isEqualTo(saalplanId);
        assertThat(platzEntity.getId().getReihe()).isEqualTo(reiheNr.nummer());
        assertThat(platzEntity.getId().getPlatz()).isEqualTo(platzNr.nummer());
        assertThat(platzEntity.isIstVerkauft()).isTrue();
        assertThat(platzEntity.getReservierung()).isNull();
    }
}