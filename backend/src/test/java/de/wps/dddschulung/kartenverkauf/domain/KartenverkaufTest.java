package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.persistence.SaalplanStapelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KartenverkaufTest {

    @Mock
    SaalplanStapelImpl saalplanStapelMock;

    private Vorstellung vorstellung1;
    private Vorstellung vorstellung2;
    private Vorstellung vorstellung3;
    private List<Platz> platzListe = new ArrayList<Platz>();

    @BeforeEach
    public void setup() {
        var date1 = LocalDateTime.parse("2025-03-18T14:30:00");
        var date2 = LocalDateTime.parse("2025-03-17T15:30:00");
        var date3 = LocalDateTime.parse("2025-03-19T15:30:00");

        for (int reihe = 1; reihe <= 3; reihe++) {
            for (int platz = 1; platz <= 6; platz++) {
                platzListe.add(new Platz((long) ((reihe * 10) + platz), platz, reihe, true, null));
            }
        }

        var saal1 = new Saal(1L, "großer Saal");
        var saal2 = new Saal(2L, "kleiner Saal");
        var saal3 = new Saal(3L, "Keller");

        vorstellung1 = new Vorstellung(1L, saal1, date1);
        vorstellung2 = new Vorstellung(2L, saal2, date2);
        vorstellung3 = new Vorstellung(3L, saal3, date3);

        var saalplan1 = new Saalplan(1L, vorstellung1, platzListe);
//        var saalplan2 = new Saalplan(1L, vorstellung2, Arrays.asList(platz3, platz4));
//        var saalplan3 = new Saalplan(1L, vorstellung3, Arrays.asList(platz5, platz6));


        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung1)).thenReturn(saalplan1);
//        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung2)).thenReturn(saalplan2);
//        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung3)).thenReturn(saalplan3);

    }

    @Test
    public void sucheZusammenhaengendePlaetze_existsZusammenhaengendePlaetze_returnsCorrectPlaetze() throws NoSuchFieldException, IllegalAccessException {
        // arrange
        var anzahlGewuenschtePlaetze = 4;
        var belegtFeld = platzListe.getFirst().getClass().getDeclaredField("belegt");
        belegtFeld.setAccessible(true);
        belegtFeld.set(platzListe.get(0), false);
        belegtFeld.set(platzListe.get(1), false);
        belegtFeld.set(platzListe.get(2), false);
        belegtFeld.set(platzListe.get(3), false);
        belegtFeld.set(platzListe.get(8), false);
        belegtFeld.set(platzListe.get(9), false);
        var geholterSaalplan1 = saalplanStapelMock.holeSaalplan(vorstellung1);

        // act
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = geholterSaalplan1.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

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
        var belegtFeld = platzListe.getFirst().getClass().getDeclaredField("belegt");
        belegtFeld.setAccessible(true);
        belegtFeld.set(platzListe.get(0), false);
        belegtFeld.set(platzListe.get(9), false);
        var geholterSaalplan1 = saalplanStapelMock.holeSaalplan(vorstellung1);

        // act
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = geholterSaalplan1.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        assertThat(zusammenhaengendePlaetze.getPlaetze()).hasSize(0);
    }
}
