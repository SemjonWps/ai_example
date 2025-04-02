package de.wps.dddschulung.kartenverkauf.persistence.mappers;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.VorstellungEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
class PlatzMapperTest {

    PlatzMapper platzMapper = new PlatzMapperImpl();

    long id = 1L;
    Sitz sitz = new Sitz(42);
    Reihe reihe = new Reihe(23);
    boolean istVerkauft = false;
    Reservierungsnummer reservierungsnummer = new Reservierungsnummer("reservierungsnummer");
    private final long vorstellungId = 2L;
    private final UUID vorstellungUuid = UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74");
    private final LocalDateTime anfangszeit = LocalDateTime.parse("2025-03-17T15:30:00");
    private final String saalName = "Großer Saal";
    private final VorstellungEntity vorstellungEntity = new VorstellungEntity(vorstellungId, vorstellungUuid, anfangszeit, saalName);

    @Test
    public void testPlatzEntityToPlatz() {
        // arrange
        PlatzEntity platzEntity = new PlatzEntity(id, sitz.platznummer(), reihe.reihennummer(), istVerkauft, reservierungsnummer.reservierungsnummer(), vorstellungEntity);

        // act
        Platz platz = platzMapper.platzEntityToPlatz(platzEntity);

        // assert
        assertThat(platz.getSitz()).isEqualTo(sitz);
        assertThat(platz.getId()).isEqualTo(id);
        assertThat(platz.getReihe()).isEqualTo(reihe);
        assertThat(platz.istVerkauft()).isEqualTo(istVerkauft);
        assertThat(platz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
        assertThat(platz.getVorstellungUUID()).isEqualTo(vorstellungUuid);
    }

    @Test
    public void testPlatzToPlatzEntity() {
        // arrange
        Platz platz = new Platz(id, sitz, reihe, istVerkauft, reservierungsnummer, vorstellungUuid);

        // act
        PlatzEntity platzEntity = platzMapper.platzToPlatzEntity(platz);

        // assert
        assertThat(platzEntity.getId()).isEqualTo(id);
        assertThat(platzEntity.getPlatznummer()).isEqualTo(sitz.platznummer());
        assertThat(platzEntity.getReihennummer()).isEqualTo(reihe.reihennummer());
        assertThat(platzEntity.istVerkauft()).isEqualTo(istVerkauft);
        assertThat(platzEntity.getReservierungsnummer()).isEqualTo(reservierungsnummer.reservierungsnummer());
        assertThat(platzEntity.getVorstellung().getId()).isEqualTo(vorstellungId);
        assertThat(platzEntity.getVorstellung().getUuid()).isEqualTo(vorstellungUuid);
        assertThat(platzEntity.getVorstellung().getAnfangszeit()).isEqualTo(anfangszeit);
    }

}