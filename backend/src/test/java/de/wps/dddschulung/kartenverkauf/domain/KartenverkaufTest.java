package de.wps.dddschulung.kartenverkauf.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class KartenverkaufTest {

    @Test
    void testKartenverkauf() {
        var date = LocalDateTime.parse("2025-03-17T15:30:00");
        var platz1 = new Platz(1L, 1, false, "oiduf");
        var platz2 = new Platz(2L, 2, false, "oiduf");
        var zusammenhaengendePlaetzeMock = new ZusammenhaengendePlaetze(1L, Arrays.asList(platz1, platz2));
        var saal1 = new Saal(1L, "großer Saal");
        var saal2 = new Saal(2L, "kleiner Saal");
        var saal3 = new Saal(3L, "Keller");
        var saalplanMock = new Saalplan(1L, date, saal2, Arrays.asList(zusammenhaengendePlaetzeMock));
        var vorstellung = new Vorstellung(1L, saal2, date);

        var saalplanstapel = new SaalplanStapel(1L, Arrays.asList(saalplanMock));
        var saalplan = saalplanstapel.holeSaalplan(vorstellung);

        List<ZusammenhaengendePlaetze> zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(2);
        zusammenhaengendePlaetze.get(0).markiereAlsVerkauft();
        zusammenhaengendePlaetze.get(1).markiereAlsVerkauft();
        saalplanstapel.legeZurueck(saalplan);
    }
}
