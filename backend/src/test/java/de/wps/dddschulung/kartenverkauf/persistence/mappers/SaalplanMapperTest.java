package de.wps.dddschulung.kartenverkauf.persistence.mappers;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaalplanMapperTest {
    SaalplanMapper saalplanMapper = new SaalplanMapperImpl();

    private final long saalplanId = 2L;
    private final int platznummer = 1;
    private final Sitz sitz = new Sitz(platznummer);
    private final int reihennummer = 42;
    private final Reihe reihe = new Reihe(reihennummer);
    private final boolean istVerkauft = false;
    private final String reservierungsnummerString = "reservierungsnummer";
    private final Reservierungsnummer reservierungsnummer = new Reservierungsnummer(reservierungsnummerString);
    private final long platzId = 3L;
    private final UUID vorstellungUUID = UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74");
    private final PlatzEntity platzEntity = new PlatzEntity(platzId, platznummer, reihennummer, istVerkauft, reservierungsnummerString, saalplanId);
    private final List<PlatzEntity> platzEntities = List.of(platzEntity);

    @Test
    public void saalplanToSaalplanEntity() {
        // arrange
        List<Platz> plaetze = new ArrayList<>(List.of(new Platz(platzId, sitz, reihe, istVerkauft, reservierungsnummer)));
        Saalplan saalplan = new Saalplan(saalplanId, vorstellungUUID, plaetze);

        // act
        SaalplanEntity saalplanEntity = saalplanMapper.saalplanToSaalplanEntity(saalplan);

        // assert
        assertThat(saalplanEntity.getId()).isEqualTo(saalplanId);
        assertThat(saalplanEntity.getVorstellungUUID()).isEqualTo(vorstellungUUID);
        assertThat(saalplanEntity.getPlaetze()).isEqualTo(platzEntities);
    }

    @Test
    public void saalplanEntityToSaalplan() {
        // arrange
        SaalplanEntity saalplanEntity = new SaalplanEntity(saalplanId, platzEntities, vorstellungUUID);

        // act
        Saalplan saalplan = saalplanMapper.saalplanEntityToSaalplan(saalplanEntity);

        // assert
        assertThat(saalplan.getId()).isEqualTo(saalplanId);
        Platz mappedPlatz = saalplan.getPlaetze().get(reihe).getFirst();
        assertThat(mappedPlatz.getReihe()).isEqualTo(reihe);
        assertThat(mappedPlatz.getSitz()).isEqualTo(sitz);
        assertThat(mappedPlatz.getId()).isEqualTo(platzId);
        assertThat(mappedPlatz.istVerkauft()).isEqualTo(istVerkauft);
        assertThat(mappedPlatz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
        assertThat(saalplan.getVorstellungUUID()).isEqualTo(vorstellungUUID);
    }
}