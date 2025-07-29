package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.persistence.model.SaalplanEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SaalplanMapperTest {
    SaalplanMapper saalplanMapper = new SaalplanMapperImpl();

    private final long saalplanId = 2L;
    private final int platzNrInt = 1;
    private final PlatzNummer platzNr = new PlatzNummer(platzNrInt);
    private final int reiheNrInt = 42;
    private final ReiheNummer reiheNr = new ReiheNummer(reiheNrInt);
    private final boolean istVerkauft = false;
    private final String reservierungsnummerString = "reservierungsnummer";
    private final Reservierungsnummer reservierungsnummer = new Reservierungsnummer(reservierungsnummerString);
    private final long platzId = 3L;
    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74"));
    private final PlatzEntity platzEntity = new PlatzEntity(platzId, platzNrInt, reiheNrInt, istVerkauft, reservierungsnummerString);
    private final List<PlatzEntity> platzEntities = List.of(platzEntity);

    @Test
    public void saalplanToSaalplanEntity() {
        // arrange
        var plaetze = List.of(new Platz(platzId, new PlatzId(reiheNr, platzNr), istVerkauft, reservierungsnummer));
        var saalplan = new Saalplan(saalplanId, vorstellungId, plaetze);

        // act
        var saalplanEntity = saalplanMapper.saalplanToSaalplanEntity(saalplan);

        // assert
        assertThat(saalplanEntity.getId()).isEqualTo(saalplanId);
        assertThat(saalplanEntity.getVorstellungUUID()).isEqualTo(vorstellungId.uuid());
        assertThat(saalplanEntity.getPlaetze()).isEqualTo(platzEntities);
    }

    @Test
    public void saalplanEntityToSaalplan() {
        // arrange
        var saalplanEntity = new SaalplanEntity(saalplanId, vorstellungId.uuid(), platzEntities);

        // act
        var saalplan = saalplanMapper.saalplanEntityToSaalplan(saalplanEntity);

        // assert
        assertThat(saalplan.getId()).isEqualTo(saalplanId);
        assertThat(saalplan.getVorstellungId()).isEqualTo(vorstellungId);
        Platz mappedPlatz = saalplan.getPlaetze().get(reiheNr).get(platzNr);
        assertThat(mappedPlatz.getPlatzId().reiheNr()).isEqualTo(reiheNr);
        assertThat(mappedPlatz.getPlatzId().platzNr()).isEqualTo(platzNr);
        assertThat(mappedPlatz.getId()).isEqualTo(platzId);
        assertThat(mappedPlatz.isIstVerkauft()).isEqualTo(istVerkauft);
        assertThat(mappedPlatz.getReservierungsnummer()).isEqualTo(reservierungsnummer);
    }
}