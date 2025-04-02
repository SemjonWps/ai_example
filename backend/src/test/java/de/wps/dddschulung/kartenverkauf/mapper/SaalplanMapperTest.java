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
    private final int platznummer = 1;
    private final Sitz sitz = new Sitz(platznummer);
    private final int reihennummer = 42;
    private final Reihe reihe = new Reihe(reihennummer);
    private final boolean istVerkauft = false;
    private final String reservierungsnummerString = "reservierungsnummer";
    private final Reservierungsnummer reservierungsnummer = new Reservierungsnummer(reservierungsnummerString);
    private final long platzId = 3L;
    private final List<Platz> plaetze = new ArrayList<>(List.of(new Platz(platzId, sitz, reihe, istVerkauft, reservierungsnummer)));
    private final String saalName = "Großer Saal";
    private final long saalId = 5L;
    private final String filmnameString = "Back to the Futura";
    private final PlatzEntity platzEntity = new PlatzEntity(platzId, platznummer, reihennummer, istVerkauft, reservierungsnummerString);
    private final List<PlatzEntity> platzEntities = List.of(platzEntity);

    @Test
    public void saalplanToSaalplanEntity() {
        // arrange
        Filmname filmname = new Filmname(filmnameString);
        Saal saal = new Saal(saalId, saalName);
        Vorstellung vorstellung = new Vorstellung(saal, beginn, filmname);
        Saalplan saalplan = new Saalplan(saalplanId, vorstellung, plaetze);

        // act
        SaalplanEntity saalplanEntity = saalplanMapper.saalplanToSaalplanEntity(saalplan);

        // assert
        assertThat(saalplanEntity.getId()).isEqualTo(saalplanId);
        assertThat(saalplanEntity.getAnfangszeit()).isEqualTo(anfangszeit);
        assertThat(saalplanEntity.getPlaetze()).isEqualTo(platzEntities);
        assertThat(saalplanEntity.getSaal().getName()).isEqualTo(saalplan.getVorstellung().saal().name());
    }

    @Test
    public void saalplanEntityToSaalplan() {
        // arrange
        SaalEntity saalEntity = new SaalEntity(saalId, saalName);
        SaalplanEntity saalplanEntity = new SaalplanEntity(saalplanId, anfangszeit, filmnameString, platzEntities, saalEntity);

        // act
        Saalplan saalplan = saalplanMapper.saalplanEntityToSaalplan(saalplanEntity);

        // assert
        assertThat(saalplan.getId()).isEqualTo(saalplanId);
        assertThat(saalplan.getVorstellung().anfangszeit()).isEqualTo(beginn);
        Platz mappedPlatz = saalplan.getPlaetze().get(reihe).getFirst();
        assertThat(mappedPlatz.getReihe()).isEqualTo(reihe);
        assertThat(mappedPlatz.getSitz()).isEqualTo(sitz);
        assertThat(mappedPlatz.getId()).isEqualTo(platzId);
        assertThat(mappedPlatz.istVerkauft()).isEqualTo(istVerkauft);
        assertThat(mappedPlatz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
        assertThat(saalplan.getVorstellung().saal().name()).isEqualTo(saalplanEntity.getSaal().getName());
    }
}