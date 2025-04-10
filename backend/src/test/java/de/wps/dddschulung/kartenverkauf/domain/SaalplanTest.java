package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SaalplanTest {

    private final UUID vorstellungUUID = UUID.fromString("a095c8f6-6fa2-4f2e-acf1-52cee0698e74");
    private final List<Platz> platzListe = new ArrayList<>();
    private final long saalplanId = 1L;
    private Saalplan saalplan;

    @BeforeEach
    public void setup() {

        saalplan = new Saalplan(saalplanId, null, new ArrayList<>());

        for (int reihe = 1; reihe <= 4; reihe++) {
            for (int platznummer = 1; platznummer <= 6; platznummer++) {
                platzListe.add(new Platz((long) ((reihe * 10) + platznummer), new Sitz(platznummer), new Reihe(reihe), true, null, saalplanId));
            }
        }

    }

    private void befuelleSaalplan() {
        saalplan = new Saalplan(1L, vorstellungUUID, platzListe);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_existsZusammenhaengendePlaetzeInSecondToLastReihe_returnsCorrectPlaetze() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 4;
        var vorletzteReihe = 3;
        var belegtFeld = platzListe.getFirst().getClass().getDeclaredField("istVerkauft");
        belegtFeld.setAccessible(true);

        // Reihe 1 mit 4 freien Plätzen
        belegtFeld.set(platzListe.get(0), false);
        belegtFeld.set(platzListe.get(1), false);
        belegtFeld.set(platzListe.get(2), false);
        belegtFeld.set(platzListe.get(3), false);
        // Reihe 3 mit 4 freien Plätzen
        belegtFeld.set(platzListe.get(12), false);
        belegtFeld.set(platzListe.get(13), false);
        belegtFeld.set(platzListe.get(14), false);
        belegtFeld.set(platzListe.get(15), false);
        // Reihe 4 mit 4 freien, nicht zusammenhängenden Plätzen
        belegtFeld.set(platzListe.get(18), false);
        belegtFeld.set(platzListe.get(19), false);
        belegtFeld.set(platzListe.get(20), false);
        belegtFeld.set(platzListe.get(22), false);
        befuelleSaalplan();

        // act
        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        //assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(anzahlGewuenschtePlaetze);
        Set<Platz> plaetzeSet = new HashSet<>(zusammenhaengendePlaetze.plaetze());
        assertThat(plaetzeSet).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.plaetze().getLast().getSitz().platznummer() - zusammenhaengendePlaetze.plaetze().getFirst().getSitz().platznummer()).isEqualTo(anzahlGewuenschtePlaetze - 1);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(Platz::istFrei);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(platz -> platz.getReihe().reihennummer() == vorletzteReihe);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_doesNotExistZusammenhaengendePlaetze_returnsObjectWithEmptyList() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 2;
        var belegtFeld = platzListe.getFirst().getClass().getDeclaredField("istVerkauft");
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
            plaetze.add(new Platz((long) platznummer, new Sitz(platznummer), new Reihe(1), false, null, saalplanId));
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
            plaetze.add(new Platz((long) platznummer, new Sitz(platznummer), new Reihe(1), false, null, saalplanId));
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
        var platz1 = new Platz(1L, new Sitz(1), new Reihe(1), false, reservierungsnummer, saalplanId);
        var platz3 = new Platz(3L, new Sitz(3), new Reihe(2), false, null, saalplanId);
        var platz2 = new Platz(2L, new Sitz(2), new Reihe(2), false, reservierungsnummer, saalplanId);
        var platz4 = new Platz(4L, new Sitz(4), new Reihe(2), false, null, saalplanId);
        var platz5 = new Platz(5L, new Sitz(5), new Reihe(2), false, new Reservierungsnummer("andereReservierungsnummer"), saalplanId);

        lokalePlatzliste.add(platz1);
        lokalePlatzliste.add(platz2);
        lokalePlatzliste.add(platz3);
        lokalePlatzliste.add(platz4);
        lokalePlatzliste.add(platz5);

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
        var lokalePlatzliste = new ArrayList<Platz>();
        var platz1 = new Platz(1L, new Sitz(1), new Reihe(1), true, reservierungsnummer, saalplanId);
        var platz2 = new Platz(2L, new Sitz(2), new Reihe(2), true, reservierungsnummer, saalplanId);
        var platz3 = new Platz(3L, new Sitz(3), new Reihe(2), false, null, saalplanId);
        var platz4 = new Platz(4L, new Sitz(4), new Reihe(2), false, null, saalplanId);
        var platz5 = new Platz(5L, new Sitz(5), new Reihe(2), false, reservierungsnummer, saalplanId);
        var platz6 = new Platz(6L, new Sitz(6), new Reihe(3), true, null, saalplanId);

        lokalePlatzliste.add(platz1);
        lokalePlatzliste.add(platz2);
        lokalePlatzliste.add(platz3);
        lokalePlatzliste.add(platz4);
        lokalePlatzliste.add(platz5);
        lokalePlatzliste.add(platz6);

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
