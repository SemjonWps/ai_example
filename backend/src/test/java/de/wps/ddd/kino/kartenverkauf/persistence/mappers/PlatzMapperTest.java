package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlatzMapperTest {

    PlatzMapper platzMapper = new PlatzMapperImpl();

    int saalplanId = 2;
    ReiheNummer reiheNr = new ReiheNummer(23);
    PlatzNummer platzNr = new PlatzNummer(42);
    Reservierungsnummer reservierungsnummer = new Reservierungsnummer("reservierungsnummer");

    @Test
    public void testPlatzEntityToPlatz() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(new PlatzEntity.Id(saalplanId, reiheNr.nummer(), platzNr.nummer()), false, reservierungsnummer.nummer());

        // act
        Platz platz = platzMapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getPlatzId().reiheNr().nummer()).isEqualTo(reiheNr.nummer());
        assertThat(platz.getPlatzId().platzNr().nummer()).isEqualTo(platzNr.nummer());
        assertThat(platz.isIstVerkauft()).isFalse();
        assertThat(platz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
    }

    @Test
    public void testPlatzEntityToPlatz_reservierungsnummer_null() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(new PlatzEntity.Id(saalplanId, reiheNr.nummer(), platzNr.nummer()), true, null);

        // act
        Platz platz = platzMapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getPlatzId().reiheNr().nummer()).isEqualTo(reiheNr.nummer());
        assertThat(platz.getPlatzId().platzNr().nummer()).isEqualTo(platzNr.nummer());
        assertThat(platz.isIstVerkauft()).isTrue();
        assertThat(platz.getReservierungsnummer()).isNull();
    }

    @Test
    public void testPlatzToPlatzEntity() {
        // arrange
        Platz platz = new Platz(new PlatzId(reiheNr, platzNr), false, reservierungsnummer);

        // act
        PlatzEntity platzEntity = platzMapper.platzToPlatzEntity(platz, saalplanId);

        // assert
        assertThat(platzEntity.getId().getSaalplanId()).isEqualTo(saalplanId);
        assertThat(platzEntity.getId().getReiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzEntity.getId().getPlatzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzEntity.isIstVerkauft()).isFalse();
        assertThat(platzEntity.getReservierungsnummer()).isEqualTo(reservierungsnummer.nummer());
    }

    @Test
    public void testPlatzToPlatzEntity_reservierungsnummer_null() {
        // arrange
        Platz platz = new Platz(new PlatzId(reiheNr, platzNr), true, null);

        // act
        PlatzEntity platzEntity = platzMapper.platzToPlatzEntity(platz, saalplanId);

        // assert
        assertThat(platzEntity.getId().getSaalplanId()).isEqualTo(saalplanId);
        assertThat(platzEntity.getId().getReiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzEntity.getId().getPlatzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzEntity.isIstVerkauft()).isTrue();
        assertThat(platzEntity.getReservierungsnummer()).isNull();
    }

}