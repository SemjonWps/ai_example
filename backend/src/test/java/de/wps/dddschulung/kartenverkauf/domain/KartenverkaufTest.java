package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.persistence.SaalplanStapelImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KartenverkaufTest {

    @Mock
    SaalplanStapelImpl saalplanStapelMock;

    private Vorstellung vorstellung1;
    private Vorstellung vorstellung2;
    private Vorstellung vorstellung3;

    @BeforeEach
    public void setup() {
        var date1 = LocalDateTime.parse("2025-03-18T14:30:00");
        var date2 = LocalDateTime.parse("2025-03-17T15:30:00");
        var date3 = LocalDateTime.parse("2025-03-19T15:30:00");
        var anzahlGewuenschtePlaetze = 2;

        // TODO for loop
        var platz1 = new Platz(1L, 1, 1, false, null);
        var platz2 = new Platz(2L, 2, 1, false, null);
        var platz3 = new Platz(3L, 3, 2, false, null);
        var platz4 = new Platz(4L, 4, 2, true, null);
        var platz5 = new Platz(5L, 5, 3, false, null);
        var platz6 = new Platz(6L, 6, 2, false, null);

        var saal1 = new Saal(1L, "großer Saal");
        var saal2 = new Saal(2L, "kleiner Saal");
        var saal3 = new Saal(3L, "Keller");

        vorstellung1 = new Vorstellung(1L, saal1, date1);
        vorstellung2 = new Vorstellung(2L, saal2, date2);
        vorstellung3 = new Vorstellung(3L, saal3, date3);

        // TODO saalplan mit Plätzen befüllen
        var saalplan1 = new Saalplan(1L, vorstellung1, Arrays.asList(platz1, platz2));
//        var saalplan2 = new Saalplan(1L, vorstellung2, Arrays.asList(platz3, platz4));
//        var saalplan3 = new Saalplan(1L, vorstellung3, Arrays.asList(platz5, platz6));


        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung1)).thenReturn(saalplan1);
//        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung2)).thenReturn(saalplan2);
//        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung3)).thenReturn(saalplan3);

    }

    @Test
    public void testKartenverkauf() {
        var anzahlGewuenschtePlaetze = 4;

        var geholterSaalplan1 = saalplanStapelMock.holeSaalplan(vorstellung1);
        assertThat(geholterSaalplan1.getVorstellung()).isEqualTo(vorstellung1);

        ZusammenhaengendePlaetze zusammenhaengendePlaetze = geholterSaalplan1.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.getPlaetze()).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.getPlaetze()).allMatch(Platz::istFrei);

        geholterSaalplan1.markiereAlsVerkauft(zusammenhaengendePlaetze);
        assertThat(zusammenhaengendePlaetze.getPlaetze()).allMatch(Platz::istBelegt);

    }
}
