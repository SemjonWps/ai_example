package de.wps.ddd.kino.kartenverkauf.domain;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.*;
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
                Reihennummer reihennummer = new Reihennummer(reihe);
                Platznummer sitzplatznummer = new Platznummer(platznummer);
                PlatzId platzId = new PlatzId(reihennummer, sitzplatznummer);
                platzListe.add(new Platz((long) ((reihe * 10) + platznummer), platzId, true, null, saalplanId));
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
        assertThat(zusammenhaengendePlaetze.plaetze()).hasSize(anzahlGewuenschtePlaetze);
        Set<PlatzId> plaetzeSet = new HashSet<>(zusammenhaengendePlaetze.plaetze());
        assertThat(plaetzeSet).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.plaetze().getLast().platznummer().nummer() - zusammenhaengendePlaetze.plaetze().getFirst().platznummer().nummer()).isEqualTo(anzahlGewuenschtePlaetze - 1);
        assertThat(zusammenhaengendePlaetze.plaetze()).allMatch(platz -> platz.reihennummer().nummer() == vorletzteReihe);
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
        Reihennummer reihennummer = new Reihennummer(1);
        Platznummer platznummer1 = new Platznummer(1);
        Platznummer platznummer2 = new Platznummer(2);
        Platznummer platznummer3 = new Platznummer(3);
        var platzId1 = new PlatzId(reihennummer, platznummer1);
        var platzId2 = new PlatzId(reihennummer, platznummer2);
        var platzId3 = new PlatzId(reihennummer, platznummer3);

        var plaetze = new ArrayList<PlatzId>();
        plaetze.add(platzId1);
        plaetze.add(platzId2);
        plaetze.add(platzId3);

        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(plaetze);

        // act
        saalplan.markiereAlsVerkauft(zusammenhaengendePlaetze);

        // assert
        assertThat(saalplan.getPlaetze().get(0).get(1).isIstVerkauft()).isTrue();
        assertThat(saalplan.getPlaetze().get(0).get(2).isIstVerkauft()).isTrue();
        assertThat(saalplan.getPlaetze().get(0).get(3).isIstVerkauft()).isTrue();
    }

    @Test
    public void markiereAlsReserviert() {
        // arrange
        Reihennummer reihennummer = new Reihennummer(1);
        Platznummer platznummer1 = new Platznummer(1);
        Platznummer platznummer2 = new Platznummer(2);
        Platznummer platznummer3 = new Platznummer(3);
        var platzId1 = new PlatzId(reihennummer, platznummer1);
        var platzId2 = new PlatzId(reihennummer, platznummer2);
        var platzId3 = new PlatzId(reihennummer, platznummer3);

        var plaetze = new ArrayList<PlatzId>();
        plaetze.add(platzId1);
        plaetze.add(platzId2);
        plaetze.add(platzId3);

        var zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(plaetze);

        var reservierungsnummer = new Reservierungsnummer("reservierungsnummer");

        // act
        saalplan.markiereAlsReserviert(zusammenhaengendePlaetze, reservierungsnummer);

        // assert
        assertThat(saalplan.getPlaetze().get(0).get(1).istBelegt()).isTrue();
        assertThat(saalplan.getPlaetze().get(0).get(1).getReservierungsnummer()).isEqualTo(reservierungsnummer);

        assertThat(saalplan.getPlaetze().get(0).get(2).istBelegt()).isTrue();
        assertThat(saalplan.getPlaetze().get(0).get(2).getReservierungsnummer()).isEqualTo(reservierungsnummer);

        assertThat(saalplan.getPlaetze().get(0).get(3).istBelegt()).isTrue();
        assertThat(saalplan.getPlaetze().get(0).get(3).getReservierungsnummer()).isEqualTo(reservierungsnummer);
    }

    @Test
    public void markiereAlsVerkauft_MitReservierungsnummer() {
        // arrange
        var reservierungsnummer = new Reservierungsnummer("reservierungsnummer");
        var lokalePlatzliste = new ArrayList<Platz>();
        var platz1 = new Platz(1L, new PlatzId(new Reihennummer(1), new Platznummer(1)), false, reservierungsnummer, saalplanId);
        var platz3 = new Platz(3L, new PlatzId(new Reihennummer(2), new Platznummer(3)), false, null, saalplanId);
        var platz2 = new Platz(2L, new PlatzId(new Reihennummer(2), new Platznummer(2)), false, reservierungsnummer, saalplanId);
        var platz4 = new Platz(4L, new PlatzId(new Reihennummer(2), new Platznummer(4)), false, null, saalplanId);
        var platz5 = new Platz(5L, new PlatzId(new Reihennummer(2), new Platznummer(5)), false, new Reservierungsnummer("andereReservierungsnummer"), saalplanId);

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
        var platz1 = new Platz(1L, new PlatzId(new Reihennummer(1), new Platznummer(1)), true, reservierungsnummer, saalplanId);
        var platz2 = new Platz(2L, new PlatzId(new Reihennummer(2), new Platznummer(2)), true, reservierungsnummer, saalplanId);
        var platz3 = new Platz(3L, new PlatzId(new Reihennummer(2), new Platznummer(3)), false, null, saalplanId);
        var platz4 = new Platz(4L, new PlatzId(new Reihennummer(2), new Platznummer(4)), false, null, saalplanId);
        var platz5 = new Platz(5L, new PlatzId(new Reihennummer(2), new Platznummer(5)), false, reservierungsnummer, saalplanId);
        var platz6 = new Platz(6L, new PlatzId(new Reihennummer(3), new Platznummer(6)), true, null, saalplanId);

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
