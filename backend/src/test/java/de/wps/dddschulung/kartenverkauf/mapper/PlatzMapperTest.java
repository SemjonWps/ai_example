package de.wps.dddschulung.kartenverkauf.mapper;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import de.wps.dddschulung.kartenverkauf.persistence.mapper.PlatzMapper;
import de.wps.dddschulung.kartenverkauf.persistence.mapper.PlatzMapperImpl;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
class PlatzMapperTest {

    PlatzMapper platzMapper = new PlatzMapperImpl();

    long id = 1L;
    Sitz sitz = new Sitz(42);
    Reihe reihe = new Reihe(23);
    boolean istVerkauft = false;
    Reservierungsnummer reservierungsnummer = new Reservierungsnummer("reservierungsnummer");

    @Test
    public void testPlatzEntityToPlatz() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(id, sitz.platznummer(), reihe.reihennummer(), istVerkauft, reservierungsnummer.reservierungsnummer());

        // act
        Platz platz = platzMapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getSitz()).isEqualTo(sitz);
        assertThat(platz.getId()).isEqualTo(id);
        assertThat(platz.getReihe()).isEqualTo(reihe);
        assertThat(platz.istVerkauft()).isEqualTo(istVerkauft);
        assertThat(platz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
    }

    @Test
    public void testPlatzToPlatzEntity() {
        // arrange
        Platz platz = new Platz(id, sitz, reihe, istVerkauft, reservierungsnummer);

        // act
        PlatzEntity platzEntity = platzMapper.platzToPlatzEntity(platz);

        // assert
        assertThat(platzEntity.getId()).isEqualTo(id);
        assertThat(platzEntity.getPlatznummer()).isEqualTo(sitz.platznummer());
        assertThat(platzEntity.getReihennummer()).isEqualTo(reihe.reihennummer());
        assertThat(platzEntity.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(platzEntity.getReservierungsnummer()).isEqualTo(reservierungsnummer.reservierungsnummer());
    }

}