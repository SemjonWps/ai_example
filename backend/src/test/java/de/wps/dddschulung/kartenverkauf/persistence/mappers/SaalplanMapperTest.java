package de.wps.dddschulung.kartenverkauf.persistence.mappers;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.*;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.VorstellungEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final String saalName = "Großer Saal";
    private final String filmnameString = "Back to the Futura";
    private final long vorstellungId = 4L;
    private final UUID vorstellungUuid = UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74");
    private final VorstellungEntity vorstellungEntity = new VorstellungEntity(vorstellungId, vorstellungUuid, anfangszeit, saalName);
    private final PlatzEntity platzEntity = new PlatzEntity(platzId, platznummer, reihennummer, istVerkauft, reservierungsnummerString, vorstellungEntity);
    private final List<PlatzEntity> platzEntities = List.of(platzEntity);

    @Test
    public void saalplanToSaalplanEntity() {
        // arrange
        List<Platz> plaetze = new ArrayList<>(List.of(new Platz(platzId, sitz, reihe, istVerkauft, reservierungsnummer, vorstellungUuid)));
        Filmname filmname = new Filmname(filmnameString);
        Saal saal = new Saal(saalName);
        Vorstellung vorstellung = new Vorstellung(vorstellungUuid, saal, beginn, filmname);
        Saalplan saalplan = new Saalplan(saalplanId, vorstellung, plaetze);

        // act
        SaalplanEntity saalplanEntity = saalplanMapper.saalplanToSaalplanEntity(saalplan);

        // assert
        assertThat(saalplanEntity.getId()).isEqualTo(saalplanId);
        assertThat(saalplanEntity.getVorstellung().getAnfangszeit()).isEqualTo(anfangszeit);
        assertThat(saalplanEntity.getPlaetze()).isEqualTo(platzEntities);
        assertThat(saalplanEntity.getVorstellung().getSaal()).isEqualTo(saalplan.getVorstellung().saal().name());
        assertThat(saalplanEntity.getVorstellung().getUuid()).isEqualTo(vorstellungUuid);
        assertThat(saalplanEntity.getVorstellung().getId()).isEqualTo(vorstellungId);
        assertThat(saalplanEntity.getVorstellung().getAnfangszeit()).isEqualTo(anfangszeit);
    }

    @Test
    public void saalplanEntityToSaalplan() {
        // arrange
        SaalplanEntity saalplanEntity = new SaalplanEntity(saalplanId, filmnameString, platzEntities, vorstellungEntity);

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
        assertThat(saalplan.getVorstellung().saal().name()).isEqualTo(saalplanEntity.getVorstellung().getSaal());
    }
}