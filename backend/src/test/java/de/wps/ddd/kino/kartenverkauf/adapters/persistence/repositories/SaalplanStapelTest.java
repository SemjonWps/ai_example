package de.wps.ddd.kino.kartenverkauf.adapters.persistence.repositories;


import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SaalplanStapelTest {

    @Autowired
    private SaalplanStapel saalplanStapel;

    private final VorstellungId vorstellungId = new VorstellungId(UUID.fromString("f142de00-f3ec-4a42-9493-d406b3062b4a"));

    @Test
    public void holeSaalplan() {
        // act
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);

        // assert
        assertThat(saalplan).isNotNull();
        assertThat(saalplan.getVorstellungId()).isEqualTo(vorstellungId);
        int reihenAnzahl = 4;
        int platzAnzahlInReihe = 8;
        List<ReiheNummer> reihen = saalplan.getPlaetze().keySet().stream().toList();
        assertThat(reihen).hasSize(reihenAnzahl);
        saalplan.getPlaetze().forEach((reihe, plaetzeListe) -> assertThat(plaetzeListe).hasSize(platzAnzahlInReihe));
    }

    @Test
    public void legeZurueck_geaenderterSaalplan() {
        // arrange
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        var platzId = new PlatzId(new ReiheNummer(2), new PlatzNummer(3));
        assertThat(saalplan.platz(platzId).istVerkauft()).isFalse();

        // act
        saalplan.platz(platzId).markiereAlsVerkauft();
        saalplanStapel.legeZurueck(saalplan);

        // assert
        var geaenderterSaalplan = saalplanStapel.holeSaalplan(vorstellungId);
        assertThat(geaenderterSaalplan.platz(platzId).istVerkauft()).isTrue();
    }

    @Test
    public void legeZurueck_neuerSaalplan() {
        // arrange
        var vorstellungId = new VorstellungId(UUID.randomUUID());
        assertThat(saalplanStapel.holeSaalplan(vorstellungId)).isNull();

        var platzId = new PlatzId(new ReiheNummer(1), new PlatzNummer(1));
        var saalplan = new Saalplan(vorstellungId, List.of(new Platz(platzId, true, null)));

        // act
        saalplanStapel.legeZurueck(saalplan);

        // assert
        var neuerSaalplan = saalplanStapel.holeSaalplan(vorstellungId);
        assertThat(neuerSaalplan).isNotNull();
        assertThat(neuerSaalplan.platz(platzId).istVerkauft()).isTrue();

    }
}