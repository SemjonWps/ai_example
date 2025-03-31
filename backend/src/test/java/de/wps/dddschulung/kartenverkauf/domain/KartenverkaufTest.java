package de.wps.dddschulung.kartenverkauf.domain;

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
public class KartenverkaufTest {

    private Vorstellung vorstellung;
    private final List<Platz> platzListe = new ArrayList<>();
    private Saalplan saalplan;

    @BeforeEach
    public void setup() {

        saalplan = new Saalplan(1L, null, new ArrayList<>());

        for (int reihe = 1; reihe <= 3; reihe++) {
            for (int platz = 1; platz <= 6; platz++) {
                platzListe.add(new Platz((long) ((reihe * 10) + platz), platz, reihe, true, null));
            }
        }

    }

    private void befuelleSaalplan() {
        var date1 = LocalDateTime.parse("2025-03-18T14:30:00");
        var saal = new Saal(1L, "großer Saal");
        vorstellung = new Vorstellung(1L, saal, date1);
        saalplan = new Saalplan(1L, vorstellung, platzListe);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_existsZusammenhaengendePlaetze_returnsCorrectPlaetze() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 4;
        var belegtFeld = platzListe.getFirst().getClass().getDeclaredField("verkauft");
        belegtFeld.setAccessible(true);
        belegtFeld.set(platzListe.get(0), false);
        belegtFeld.set(platzListe.get(1), false);
        belegtFeld.set(platzListe.get(2), false);
        belegtFeld.set(platzListe.get(3), false);
        belegtFeld.set(platzListe.get(8), false);
        belegtFeld.set(platzListe.get(9), false);
        befuelleSaalplan();

        // act
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.getPlaetze()).hasSize(anzahlGewuenschtePlaetze);
        Set<Platz> plaetzeSet = new HashSet<>(zusammenhaengendePlaetze.getPlaetze());
        assertThat(plaetzeSet).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.getPlaetze().getLast().getPlatznummer() - zusammenhaengendePlaetze.getPlaetze().getFirst().getPlatznummer()).isEqualTo(anzahlGewuenschtePlaetze - 1);
        assertThat(zusammenhaengendePlaetze.getPlaetze()).allMatch(Platz::istFrei);
        assertThat(zusammenhaengendePlaetze.getPlaetze()).allMatch(platz -> platz.getReihe() == 1);
    }

    @Test
    public void sucheZusammenhaengendePlaetze_doesNotExistZusammenhaengendePlaetze_returnsObjectWithEmptyList() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 2;
        var belegtFeld = platzListe.getFirst().getClass().getDeclaredField("verkauft");
        belegtFeld.setAccessible(true);
        belegtFeld.set(platzListe.get(0), false);
        belegtFeld.set(platzListe.get(9), false);
        befuelleSaalplan();

        // act
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.getPlaetze()).hasSize(0);
    }

    @Test
    public void markiereAlsVerkauft() {
        // arrange
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = new ZusammenhaengendePlaetze();
        for (int platznummer = 1; platznummer <= 3; platznummer++) {
            zusammenhaengendePlaetze.getPlaetze().add(new Platz((long) platznummer, platznummer, 1, false, null));
        }

        // act
        saalplan.markiereAlsVerkauft(zusammenhaengendePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.getPlaetze()).allMatch(Platz::istBelegt);
    }

    @Test
    public void markiereAlsReserviert() {
        // arrange
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = new ZusammenhaengendePlaetze();
        for (int platznummer = 1; platznummer <= 3; platznummer++) {
            zusammenhaengendePlaetze.getPlaetze().add(new Platz((long) platznummer, platznummer, 1, false, null));
        }

        var reservierungsnummer = "reservierungsnummer";

        // act
        saalplan.markiereAlsReserviert(zusammenhaengendePlaetze, reservierungsnummer);

        // assert
        assertThat(zusammenhaengendePlaetze.getPlaetze()).allMatch(Platz::istBelegt);
        assertThat(zusammenhaengendePlaetze.getPlaetze()).allMatch(platz -> platz.getReservierungsnummer().equals(reservierungsnummer));
    }

    @Test
    public void markiereAlsVerkauft_MitReservierungsnummer() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var reservierungsnummer = "reservierungsnummer";
        var lokalePlatzliste = new ArrayList<Platz>();

        lokalePlatzliste.add(new Platz(1L, 1, 1, false, reservierungsnummer));
        lokalePlatzliste.add(new Platz(1L, 2, 2, false, reservierungsnummer));
        lokalePlatzliste.add(new Platz(1L, 3, 2, false, null));
        lokalePlatzliste.add(new Platz(1L, 4, 2, false, null));
        lokalePlatzliste.add(new Platz(1L, 5, 2, false, "andereReservierungsnummer"));

        saalplan = new Saalplan(1L, vorstellung, lokalePlatzliste);

        // act
        saalplan.markiereAlsVerkauft(reservierungsnummer);

        // assert
        var verkauftFeld = Platz.class.getDeclaredField("verkauft");
        verkauftFeld.setAccessible(true);
        assertThat(verkauftFeld.get(saalplan.getPlaetze().get(1).getFirst())).isEqualTo(true); // Reihe 1, Platz 1
        assertThat(verkauftFeld.get(saalplan.getPlaetze().get(2).getFirst())).isEqualTo(true); // Reihe 2, Platz 2
        assertThat(verkauftFeld.get(saalplan.getPlaetze().get(2).get(1))).isEqualTo(false);  // Reihe 2, Platz 3
        assertThat(verkauftFeld.get(saalplan.getPlaetze().get(2).get(2))).isEqualTo(false);  // Reihe 2, Platz 4
        assertThat(verkauftFeld.get(saalplan.getPlaetze().get(2).get(3))).isEqualTo(false); // Reihe 2, Platz 5
    }
}
