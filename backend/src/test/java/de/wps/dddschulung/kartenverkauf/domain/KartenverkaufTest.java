package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.persistence.SaalplanStapelImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class KartenverkaufTest {

    @Mock
    SaalplanStapelImpl saalplanStapelMock;

    @Test
    public void testKartenverkauf() {
        // arrange
        var date1 = LocalDateTime.parse("2025-03-18T14:30:00");
        var date2 = LocalDateTime.parse("2025-03-17T15:30:00");
        var date3 = LocalDateTime.parse("2025-03-19T15:30:00");
        var anzahlGewuenschtePlaetze = 2;

        var platz1 = new Platz(1L, 1, 1, false, null);
        var platz2 = new Platz(2L, 2, 1, false, null);
        var platz3 = new Platz(3L, 3, 2, false, null);
        var platz4 = new Platz(4L, 4, 2, true, null);
        var platz5 = new Platz(5L, 5, 3, false, null);
        var platz6 = new Platz(6L, 6, 2, false, null);

        var saal1 = new Saal(1L, "großer Saal");
        var saal2 = new Saal(2L, "kleiner Saal");
        var saal3 = new Saal(3L, "Keller");

        var saalplan1 = new Saalplan(1L, date1, saal1, Arrays.asList(platz1, platz2));
        var saalplan2 = new Saalplan(1L, date2, saal2, Arrays.asList(platz3, platz4));
        var saalplan3 = new Saalplan(1L, date3, saal3, Arrays.asList(platz5, platz6));


        var vorstellung1 = new Vorstellung(1L, saal1, date1);
        var vorstellung2 = new Vorstellung(1L, saal2, date2);
        var vorstellung3 = new Vorstellung(1L, saal3, date3);

        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung1)).thenReturn(saalplan1);
        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung2)).thenReturn(saalplan2);
        Mockito.when(saalplanStapelMock.holeSaalplan(vorstellung3)).thenReturn(saalplan3);

        var geholterSaalplan1 = saalplanStapelMock.holeSaalplan(vorstellung1);
        var geholterSaalplan2 = saalplanStapelMock.holeSaalplan(vorstellung2);
        var geholterSaalplan3 = saalplanStapelMock.holeSaalplan(vorstellung3);

        //act
        List<ZusammenhaengendePlaetze> zusammenhaengendePlaetze1 = geholterSaalplan1.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);
        List<ZusammenhaengendePlaetze> zusammenhaengendePlaetze2 = geholterSaalplan2.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);
        List<ZusammenhaengendePlaetze> zusammenhaengendePlaetze3 = geholterSaalplan3.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        // Vorstellung 1
        Assert.assertEquals(vorstellung1.getAnfangszeit(), geholterSaalplan1.getAnfangszeit());
        Assert.assertEquals(vorstellung1.getSaal().getName(), geholterSaalplan1.getSaal().getName());

        Assert.assertEquals(anzahlGewuenschtePlaetze, zusammenhaengendePlaetze1.get(0).plaetze.size());
        Assert.assertFalse(zusammenhaengendePlaetze1.get(0).plaetze.get(0).isBelegt());
        Assert.assertFalse(zusammenhaengendePlaetze1.get(0).plaetze.get(1).isBelegt());

        // Vorstellung 2 -> keine zusammenhängenden 2 Plätze weil belegt
        Assert.assertEquals(vorstellung2.getAnfangszeit(), geholterSaalplan2.getAnfangszeit());
        Assert.assertEquals(vorstellung2.getSaal().getName(), geholterSaalplan2.getSaal().getName());

        Assert.assertEquals(0, zusammenhaengendePlaetze2.size());

        // Vorstellung 3 -> keine zusammenhängenden 2 Plätze weil verschiedene Reihen
        Assert.assertEquals(vorstellung3.getAnfangszeit(), geholterSaalplan3.getAnfangszeit());
        Assert.assertEquals(vorstellung3.getSaal().getName(), geholterSaalplan3.getSaal().getName());

        Assert.assertEquals(0, zusammenhaengendePlaetze3.size());

        // act
        geholterSaalplan1.markiereAlsVerkauft(zusammenhaengendePlaetze1.get(0));

        // assert
        Assert.assertTrue(zusammenhaengendePlaetze1.get(0).plaetze.get(0).isBelegt());
        Assert.assertTrue(zusammenhaengendePlaetze1.get(0).plaetze.get(1).isBelegt());

        //saalplanStapelMock.legeZurueck(geholterSaalplan1);


    }
}
