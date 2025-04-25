package de.wps.ddd.kino.kartenverkauf.domain;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SaalplanTest {

    private final UUID vorstellungUUID = UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74");
    private Saalplan saalplan;
    private List<Platz> plaetze;

    @BeforeEach
    public void setup() {

        plaetze = new ArrayList<>();
        long id = 0;
        for (int r = 1; r <= 4; r++) {
            for (int p = 1; p <= 6; p++) {
                var reiheNr = new ReiheNummer(r);
                var platzNr = new PlatzNummer(p);
                var platzId = new PlatzId(reiheNr, platzNr);
                plaetze.add(new Platz(id++, platzId, true, null));
            }
        }

        saalplan = new Saalplan(1L, vorstellungUUID, plaetze);

    }

    @Test
    public void sucheZusammenhaengendePlaetze_existsZusammenhaengendePlaetzeInSecondToLastReihe_returnsCorrectPlaetze() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 4;
        var vorletzteReihe = 3;
        var belegtFeld = plaetze.getFirst().getClass().getDeclaredField("istVerkauft");
        belegtFeld.setAccessible(true);

        // Reihe 1 mit 4 freien Plätzen
        belegtFeld.set(plaetze.get(0), false);
        belegtFeld.set(plaetze.get(1), false);
        belegtFeld.set(plaetze.get(2), false);
        belegtFeld.set(plaetze.get(3), false);
        // Reihe 3 mit 4 freien Plätzen
        belegtFeld.set(plaetze.get(12), false);
        belegtFeld.set(plaetze.get(13), false);
        belegtFeld.set(plaetze.get(14), false);
        belegtFeld.set(plaetze.get(15), false);
        // Reihe 4 mit 4 freien, nicht zusammenhängenden Plätzen
        belegtFeld.set(plaetze.get(18), false);
        belegtFeld.set(plaetze.get(19), false);
        belegtFeld.set(plaetze.get(20), false);
        belegtFeld.set(plaetze.get(22), false);

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(anzahlGewuenschtePlaetze);
        var platzIds = zusammenhaengendePlaetze.plaetze();
        assertThat(platzIds).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.plaetze().getLast().platzNr().nummer() - zusammenhaengendePlaetze.plaetze().getFirst().platzNr().nummer()).isEqualTo(anzahlGewuenschtePlaetze - 1);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(platz -> platz.reiheNr().nummer() == vorletzteReihe);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_doesNotExistZusammenhaengendePlaetze_returnsObjectWithEmptyList() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 2;
        var belegtFeld = plaetze.getFirst().getClass().getDeclaredField("istVerkauft");
        belegtFeld.setAccessible(true);
        belegtFeld.set(plaetze.get(0), false);
        belegtFeld.set(plaetze.get(9), false);

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(0);
    }

    @Test
    public void markiereAlsVerkauft() {
        // arrange
        var reiheNr1 = new ReiheNummer(1);
        var platzNr1 = new PlatzNummer(1);
        var platzNr2 = new PlatzNummer(2);
        var platzNr3 = new PlatzNummer(3);
        var platzId1 = new PlatzId(reiheNr1, platzNr1);
        var platzId2 = new PlatzId(reiheNr1, platzNr2);
        var platzId3 = new PlatzId(reiheNr1, platzNr3);

        var plaetze = List.of(platzId1, platzId2, platzId3);

        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(plaetze);

        // act
        saalplan.markiereAlsVerkauft(zusammenhaengendePlaetze);

        // assert
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr1).isIstVerkauft()).isTrue();
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr2).isIstVerkauft()).isTrue();
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr3).isIstVerkauft()).isTrue();
    }

    @Test
    public void markiereAlsReserviert() {
        // arrange
        var reiheNr1 = new ReiheNummer(1);
        var platzNr1 = new PlatzNummer(1);
        var platzNr2 = new PlatzNummer(2);
        var platzNr3 = new PlatzNummer(3);
        var platzId1 = new PlatzId(reiheNr1, platzNr1);
        var platzId2 = new PlatzId(reiheNr1, platzNr2);
        var platzId3 = new PlatzId(reiheNr1, platzNr3);

        var plaetze = List.of(platzId1, platzId2, platzId3);

        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(plaetze);

        var reservierungsnummer = new Reservierungsnummer("reservierungsnummer");

        // act
        saalplan.markiereAlsReserviert(zusammenhaengendePlaetze, reservierungsnummer);

        // assert
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr1).istFrei()).isFalse();
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr1).getReservierungsnummer()).isEqualTo(reservierungsnummer);

        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr2).istFrei()).isFalse();
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr2).getReservierungsnummer()).isEqualTo(reservierungsnummer);

        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr3).istFrei()).isFalse();
        assertThat(saalplan.getPlaetze().get(reiheNr1).get(platzNr3).getReservierungsnummer()).isEqualTo(reservierungsnummer);
    }

    @Test
    public void markiereAlsVerkauft_MitReservierungsnummer() {
        // arrange
        var reservierungsnummer = new Reservierungsnummer("reservierungsnummer");
        var platz1 = new Platz(1L, new PlatzId(new ReiheNummer(1), new PlatzNummer(1)), false, reservierungsnummer);
        var platz3 = new Platz(3L, new PlatzId(new ReiheNummer(2), new PlatzNummer(3)), false, null);
        var platz2 = new Platz(2L, new PlatzId(new ReiheNummer(2), new PlatzNummer(2)), false, reservierungsnummer);
        var platz4 = new Platz(4L, new PlatzId(new ReiheNummer(2), new PlatzNummer(4)), false, null);
        var platz5 = new Platz(5L, new PlatzId(new ReiheNummer(2), new PlatzNummer(5)), false, new Reservierungsnummer("andereReservierungsnummer"));

        var lokalePlatzliste = List.of(platz1, platz2, platz3, platz4, platz5);

        saalplan = new Saalplan(1L, vorstellungUUID, lokalePlatzliste);

        // act
        saalplan.markiereAlsVerkauft(reservierungsnummer);

        // assert
        assertThat(platz1.isIstVerkauft()).isTrue();
        assertThat(platz2.isIstVerkauft()).isTrue();
        assertThat(platz3.isIstVerkauft()).isFalse();
        assertThat(platz4.isIstVerkauft()).isFalse();
        assertThat(platz5.isIstVerkauft()).isFalse();
    }

    @Test
    public void gebeNichtAbgeholteReservierungenFrei() {
        // arrange
        var reservierungsnummer = new Reservierungsnummer("reservierungsnummer");
        var platz1 = new Platz(1L, new PlatzId(new ReiheNummer(1), new PlatzNummer(1)), true, reservierungsnummer);
        var platz2 = new Platz(2L, new PlatzId(new ReiheNummer(2), new PlatzNummer(2)), true, reservierungsnummer);
        var platz3 = new Platz(3L, new PlatzId(new ReiheNummer(2), new PlatzNummer(3)), false, null);
        var platz4 = new Platz(4L, new PlatzId(new ReiheNummer(2), new PlatzNummer(4)), false, null);
        var platz5 = new Platz(5L, new PlatzId(new ReiheNummer(2), new PlatzNummer(5)), false, reservierungsnummer);
        var platz6 = new Platz(6L, new PlatzId(new ReiheNummer(3), new PlatzNummer(6)), true, null);

        var lokalePlatzliste = List.of(platz1, platz2, platz3, platz4, platz5, platz6);

        saalplan = new Saalplan(1L, vorstellungUUID, lokalePlatzliste);

        // act
        saalplan.gebeNichtAbgeholteReservierungenFrei();

        // assert
        assertThat(platz1.getReservierungsnummer()).isEqualTo(reservierungsnummer);
        assertThat(platz2.getReservierungsnummer()).isEqualTo(reservierungsnummer);
        assertThat(platz3.getReservierungsnummer()).isEqualTo(null);
        assertThat(platz4.getReservierungsnummer()).isEqualTo(null);
        assertThat(platz5.getReservierungsnummer()).isEqualTo(null);
        assertThat(platz6.getReservierungsnummer()).isEqualTo(null);
    }
}
