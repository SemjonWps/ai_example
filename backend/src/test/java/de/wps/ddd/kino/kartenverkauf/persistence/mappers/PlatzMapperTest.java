package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Sitz;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlatzMapperTest {

    PlatzMapper platzMapper = new PlatzMapperImpl();

    long id = 1L;
    long saalplanId = 2L;
    Sitz sitz = new Sitz(42);
    Reihe reihe = new Reihe(23);
    boolean istVerkauft = true;
    Reservierungsnummer reservierungsnummer = new Reservierungsnummer("reservierungsnummer");

    @Test
    public void testPlatzEntityToPlatz() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(id, sitz.platznummer(), reihe.reihennummer(), istVerkauft, reservierungsnummer.reservierungsnummer(), saalplanId);

        // act
        Platz platz = platzMapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getSitz()).isEqualTo(sitz);
        assertThat(platz.getId()).isEqualTo(id);
        assertThat(platz.getReihe()).isEqualTo(reihe);
        assertThat(platz.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(platz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
        assertThat(platz.getSaalplan_id()).isEqualTo(saalplanId);
    }

    @Test
    public void testPlatzToPlatzEntity() {
        // arrange
        Platz platz = new Platz(id, sitz, reihe, istVerkauft, reservierungsnummer, saalplanId);

        // act
        PlatzEntity platzEntity = platzMapper.platzToPlatzEntity(platz);
        System.out.println(platzEntity.getSaalplan_id());

        // assert
        assertThat(platzEntity.getId()).isEqualTo(id);
        assertThat(platzEntity.getPlatznummer()).isEqualTo(sitz.platznummer());
        assertThat(platzEntity.getReihennummer()).isEqualTo(reihe.reihennummer());
        assertThat(platzEntity.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(platzEntity.getReservierungsnummer()).isEqualTo(reservierungsnummer.reservierungsnummer());
        assertThat(platzEntity.getSaalplan_id()).isEqualTo(saalplanId);
    }

}