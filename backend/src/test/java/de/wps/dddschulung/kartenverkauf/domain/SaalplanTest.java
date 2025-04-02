package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Platz;
import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SaalplanTest {

    private Vorstellung vorstellung;
    private final List<Platz> platzListe = new ArrayList<>();
    private Saalplan saalplan;

    @BeforeEach
    public void setup() {

        saalplan = new Saalplan(1L, null, new ArrayList<>());

        for (int reihe = 1; reihe <= 3; reihe++) {
            for (int platznummer = 1; platznummer <= 6; platznummer++) {
                platzListe.add(new Platz((long) ((reihe * 10) + platznummer), new Sitz(platznummer), new Reihe(reihe), true, null));
            }
        }

    }

    private void befuelleSaalplan() {
        var date1 = LocalDateTime.parse("2025-03-18T14:30:00");
        var saal = new Saal(10L, "großer Saal");
        var filmname = new Filmname("Back to the Futura");
        vorstellung = new Vorstellung(saal, new Beginn(date1), filmname);
        saalplan = new Saalplan(1L, vorstellung, platzListe);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_existsZusammenhaengendePlaetze_returnsCorrectPlaetze() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 4;
        var belegtFeld = platzListe.getFirst().getClass().getDeclaredField("isVerkauft");
        belegtFeld.setAccessible(true);
        belegtFeld.set(platzListe.get(0), false);
        belegtFeld.set(platzListe.get(1), false);
        belegtFeld.set(platzListe.get(2), false);
        belegtFeld.set(platzListe.get(3), false);
        belegtFeld.set(platzListe.get(8), false);
        belegtFeld.set(platzListe.get(9), false);
        befuelleSaalplan();

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(anzahlGewuenschtePlaetze);
        Set<Platz> plaetzeSet = new HashSet<>(zusammenhaengendePlaetze.plaetze());
        assertThat(plaetzeSet).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.plaetze().getLast().getSitz().platznummer() - zusammenhaengendePlaetze.plaetze().getFirst().getSitz().platznummer()).isEqualTo(anzahlGewuenschtePlaetze - 1);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(Platz::istFrei);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(platz -> platz.getReihe().reihennummer() == 1);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_doesNotExistZusammenhaengendePlaetze_returnsObjectWithEmptyList() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 2;
        var belegtFeld = platzListe.getFirst().getClass().getDeclaredField("isVerkauft");
        belegtFeld.setAccessible(true);
        belegtFeld.set(platzListe.get(0), false);
        belegtFeld.set(platzListe.get(9), false);
        befuelleSaalplan();

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(0);
    }

    @Test
    public void markiereAlsVerkauft() {
        // arrange
        var plaetze = new ArrayList<Platz>();
        for (int platznummer = 1; platznummer <= 3; platznummer++) {
            plaetze.add(new Platz((long) platznummer, new Sitz(platznummer), new Reihe(1), false, null));
        }
        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(plaetze);

        // act
        saalplan.markiereAlsVerkauft(zusammenhaengendePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(Platz::istBelegt);
    }

    @Test
    public void markiereAlsReserviert() {
        // arrange
        var plaetze = new ArrayList<Platz>();
        for (int platznummer = 1; platznummer <= 3; platznummer++) {
            plaetze.add(new Platz((long) platznummer, new Sitz(platznummer), new Reihe(1), false, null));
        }
        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(plaetze);

        var reservierungsnummer = new Reservierungsnummer("reservierungsnummer");

        // act
        saalplan.markiereAlsReserviert(zusammenhaengendePlaetze, reservierungsnummer);

        // assert
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(Platz::istBelegt);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(platz -> platz.getReservierungsnummer().equals(reservierungsnummer));
    }

    @Test
    public void markiereAlsVerkauft_MitReservierungsnummer() {
        // arrange
        var reservierungsnummer = new Reservierungsnummer("reservierungsnummer");
        var lokalePlatzliste = new ArrayList<Platz>();
        var platz1 = new Platz(1L, new Sitz(1), new Reihe(1), false, reservierungsnummer);
        var platz3 = new Platz(3L, new Sitz(3), new Reihe(2), false, null);
        var platz2 = new Platz(2L, new Sitz(2), new Reihe(2), false, reservierungsnummer);
        var platz4 = new Platz(4L, new Sitz(4), new Reihe(2), false, null);
        var platz5 = new Platz(5L, new Sitz(5), new Reihe(2), false, new Reservierungsnummer("andereReservierungsnummer"));

        lokalePlatzliste.add(platz1);
        lokalePlatzliste.add(platz2);
        lokalePlatzliste.add(platz3);
        lokalePlatzliste.add(platz4);
        lokalePlatzliste.add(platz5);

        saalplan = new Saalplan(1L, vorstellung, lokalePlatzliste);

        // act
        saalplan.markiereAlsVerkauft(reservierungsnummer);

        // assert
        assertThat(platz1.istVerkauft()).isTrue();
        assertThat(platz2.istVerkauft()).isTrue();
        assertThat(platz3.istVerkauft()).isFalse();
        assertThat(platz4.istVerkauft()).isFalse();
        assertThat(platz5.istVerkauft()).isFalse();
    }

    @Test
    public void gebeNichtAbgeholteReservierungenFrei() {
        // arrange
        var reservierungsnummer = new Reservierungsnummer("reservierungsnummer");
        var lokalePlatzliste = new ArrayList<Platz>();
        var platz1 = new Platz(1L, new Sitz(1), new Reihe(1), true, reservierungsnummer);
        var platz2 = new Platz(2L, new Sitz(2), new Reihe(2), true, reservierungsnummer);
        var platz3 = new Platz(3L, new Sitz(3), new Reihe(2), false, null);
        var platz4 = new Platz(4L, new Sitz(4), new Reihe(2), false, null);
        var platz5 = new Platz(5L, new Sitz(5), new Reihe(2), false, reservierungsnummer);
        var platz6 = new Platz(6L, new Sitz(6), new Reihe(3), true, null);

        lokalePlatzliste.add(platz1);
        lokalePlatzliste.add(platz2);
        lokalePlatzliste.add(platz3);
        lokalePlatzliste.add(platz4);
        lokalePlatzliste.add(platz5);
        lokalePlatzliste.add(platz6);

        saalplan = new Saalplan(1L, vorstellung, lokalePlatzliste);

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
