package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.persistence.model.SaalplanEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaalplanMapperTest {
    SaalplanMapper saalplanMapper = new SaalplanMapperImpl();

    private final long saalplanId = 2L;
    private final int platznummerInt = 1;
    private final Platznummer platznummer = new Platznummer(platznummerInt);
    private final int reihennummerInt = 42;
    private final Reihennummer reihennummer = new Reihennummer(reihennummerInt);
    private final boolean istVerkauft = false;
    private final String reservierungsnummerString = "reservierungsnummer";
    private final Reservierungsnummer reservierungsnummer = new Reservierungsnummer(reservierungsnummerString);
    private final long platzId = 3L;
    private final UUID vorstellungUUID = UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74");
    private final PlatzEntity platzEntity = new PlatzEntity(platzId, platznummerInt, reihennummerInt, istVerkauft, reservierungsnummerString, saalplanId);
    private final List<PlatzEntity> platzEntities = List.of(platzEntity);

    @Test
    public void saalplanToSaalplanEntity() {
        // arrange
        List<Platz> plaetze = new ArrayList<>(List.of(new Platz(platzId, new PlatzId(reihennummer, platznummer), istVerkauft, reservierungsnummer, saalplanId)));
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
        Platz mappedPlatz = saalplan.getPlaetze().get(reihennummer).get(0);
        assertThat(mappedPlatz.getPlatzId().reihennummer().nummer()).isEqualTo(reihennummer);
        assertThat(mappedPlatz.getPlatzId().platznummer().nummer()).isEqualTo(platznummer);
        assertThat(mappedPlatz.getId()).isEqualTo(platzId);
        assertThat(mappedPlatz.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(mappedPlatz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
        assertThat(saalplan.getVorstellungUUID()).isEqualTo(vorstellungUUID);
    }
}