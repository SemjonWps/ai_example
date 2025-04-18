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

    long id = 1L;
    PlatzNummer platzNr = new PlatzNummer(42);
    ReiheNummer reiheNr = new ReiheNummer(23);
    boolean istVerkauft = true;
    Reservierungsnummer reservierungsnummer = new Reservierungsnummer("reservierungsnummer");

    @Test
    public void testPlatzEntityToPlatz() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(id, platzNr.nummer(), reiheNr.nummer(), istVerkauft, reservierungsnummer.reservierungsnummer());

        // act
        Platz platz = platzMapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getPlatzId().platzNr().nummer()).isEqualTo(platzNr.nummer());
        assertThat(platz.getId()).isEqualTo(id);
        assertThat(platz.getPlatzId().reiheNr().nummer()).isEqualTo(reiheNr.nummer());
        assertThat(platz.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(platz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
    }

    @Test
    public void testPlatzToPlatzEntity() {
        // arrange
        Platz platz = new Platz(id, new PlatzId(reiheNr, platzNr), istVerkauft, reservierungsnummer);

        // act
        PlatzEntity platzEntity = platzMapper.platzToPlatzEntity(platz);

        // assert
        assertThat(platzEntity.getId()).isEqualTo(id);
        assertThat(platzEntity.getPlatzNr()).isEqualTo(platzNr.nummer());
        assertThat(platzEntity.getReiheNr()).isEqualTo(reiheNr.nummer());
        assertThat(platzEntity.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(platzEntity.getReservierungsnummer()).isEqualTo(reservierungsnummer.reservierungsnummer());
    }

}