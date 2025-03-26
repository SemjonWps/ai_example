package de.wps.dddschulung.kartenverkauf.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KartenverkaufTest {

    @Test
    void testKartenverkauf() {
        // arrange
        var date = LocalDateTime.parse("2025-03-17T15:30:00");
        var platz1 = new Platz(1L, 1, 1, false, null);
        var platz2 = new Platz(2L, 2, 1, false, null);
        var saal1 = new Saal(1L, "großer Saal");
        var saal2 = new Saal(2L, "kleiner Saal");
        var saal3 = new Saal(3L, "Keller");
        var saalplan = new Saalplan(1L, date, saal2, Arrays.asList(platz1, platz2));
        var vorstellung = new Vorstellung(1L, saal2, date);
        var saalplanStapelMock = new ArrayList<>();
        var anzahlGewuenschtePlaetze = 2;

        //act

        List<ZusammenhaengendePlaetze> zusammenhaengendePlaetze = geholterSaalplan.sucheZusammenhaengendePlaetze(anzahlGewuenschtePlaetze);

        // assert
        assertThat(vorstellung.getAnfangszeit()).isEqualTo(geholterSaalplan.getAnfangszeit());
        assertThat(vorstellung.getSaal().getId()).isEqualTo(geholterSaalplan.getSaal().getId());
        assertThat(vorstellung.getSaal().getName()).isEqualTo(geholterSaalplan.getSaal().getName());

        assertThat(zusammenhaengendePlaetze.get(0).plaetze).hasSize(anzahlGewuenschtePlaetze);
        assertThat(zusammenhaengendePlaetze.get(0).plaetze.get(0).isBelegt()).isFalse();
        assertThat(zusammenhaengendePlaetze.get(0).plaetze.get(1).isBelegt()).isFalse();

        // act
        geholterSaalplan.markiereAlsVerkauft(zusammenhaengendePlaetze.get(0));

        // assert
        assertThat(zusammenhaengendePlaetze.get(0).plaetze.get(0).isBelegt()).isTrue();
        assertThat(zusammenhaengendePlaetze.get(0).plaetze.get(1).isBelegt()).isTrue();
        saalplanstapel.legeZurueck(geholterSaalplan);


    }
}
