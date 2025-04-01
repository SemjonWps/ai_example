package de.wps.dddschulung.kartenverkauf.mapper;

import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Platz;
import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.*;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SaalplanMapperTest {
    SaalplanMapper saalplanMapper = new SaalplanMapperImpl();

    private final long saalplanId = 2L;
    private final LocalDateTime anfangszeit = LocalDateTime.parse("2025-03-17T15:30:00");
    private final Beginn beginn = new Beginn(anfangszeit);
    private final Filmname filmname = new Filmname("Back to the Futura");
    private final Sitz sitz = new Sitz(1);
    private final Reihe reihe = new Reihe(42);
    private final boolean istVerkauft = false;
    private final Reservierungsnummer reservierungsnummer = new Reservierungsnummer("reservierungsnummer");
    private final List<Platz> plaetze = new ArrayList<>(List.of(new Platz(3L, sitz, reihe, istVerkauft, reservierungsnummer)));
    private final List<PlatzEntity> platzEntities = new ArrayList<>(List.of(new PlatzEntity(4L, sitz.platznummer(), reihe.reihennummer(), istVerkauft, reservierungsnummer.reservierungsnummer())));
    private final Saal saal = new Saal("Großer Saal");
    private final SaalEntity saalEntity = new SaalEntity(5L, "Kleiner Saal");
    Vorstellung vorstellung = new Vorstellung(saal, beginn, filmname);

    @Test
    public void saalplanToSaalplanEntity() {
        // arrange
        Saalplan saalplan = new Saalplan(saalplanId, vorstellung, plaetze);

        // act
        SaalplanEntity saalplanEntity = saalplanMapper.saalplanToSaalplanEntity(saalplan);

        // assert
        assertThat(saalplanEntity.getId()).isEqualTo(saalplanId);
        assertThat(saalplanEntity.getAnfangszeit()).isEqualTo(anfangszeit);
        assertThat(saalplanEntity.getPlaetze()).isEqualTo(plaetze);
        assertThat(saalplanEntity.getSaal().getName()).isEqualTo(saalplan.getVorstellung().saal().name());
    }

    @Test
    public void saalplanEntityToSaalplan() {
        // arrange
        SaalplanEntity saalplanEntity = new SaalplanEntity(saalplanId, anfangszeit, platzEntities, saalEntity);

        // act
        Saalplan saalplan = saalplanMapper.saalplanEntityToSaalplan(saalplanEntity);

        // assert
        assertThat(saalplan.getId()).isEqualTo(saalplanId);
        assertThat(saalplan.getVorstellung().anfangszeit()).isEqualTo(beginn);
        assertThat(saalplan.getPlaetze()).isEqualTo(plaetze);
        assertThat(saalplan.getVorstellung().saal().name()).isEqualTo(saalplanEntity.getSaal().getName());
    }
}