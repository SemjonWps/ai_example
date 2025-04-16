package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlatzMapperTest {

    PlatzMapper platzMapper = new PlatzMapperImpl();

    long id = 1L;
    long saalplanId = 2L;
    Platznummer platznummer = new Platznummer(42);
    Reihennummer reihennummer = new Reihennummer(23);
    boolean istVerkauft = true;
    Reservierungsnummer reservierungsnummer = new Reservierungsnummer("reservierungsnummer");

    @Test
    public void testPlatzEntityToPlatz() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(id, platznummer.nummer(), reihennummer.nummer(), istVerkauft, reservierungsnummer.reservierungsnummer(), saalplanId);

        // act
        Platz platz = platzMapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getPlatznummer()).isEqualTo(platznummer);
        assertThat(platz.getId()).isEqualTo(id);
        assertThat(platz.getReihennummer()).isEqualTo(reihennummer);
        assertThat(platz.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(platz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
        assertThat(platz.getSaalplan_id()).isEqualTo(saalplanId);
    }

    @Test
    public void testPlatzToPlatzEntity() {
        // arrange
        Platz platz = new Platz(id, platznummer, reihennummer, istVerkauft, reservierungsnummer, saalplanId);

        // act
        PlatzEntity platzEntity = platzMapper.platzToPlatzEntity(platz);
        System.out.println(platzEntity.getSaalplan_id());

        // assert
        assertThat(platzEntity.getId()).isEqualTo(id);
        assertThat(platzEntity.getPlatznummer()).isEqualTo(platznummer.nummer());
        assertThat(platzEntity.getReihennummer()).isEqualTo(reihennummer.nummer());
        assertThat(platzEntity.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(platzEntity.getReservierungsnummer()).isEqualTo(reservierungsnummer.reservierungsnummer());
        assertThat(platzEntity.getSaalplan_id()).isEqualTo(saalplanId);
    }

}