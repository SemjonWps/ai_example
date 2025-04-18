package de.wps.ddd.kino.kartenverkauf.persistence.repositories;


import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SaalplanStapelImplTest {

    @Autowired
    private SaalplanStapel saalplanStapel;

    UUID vorstellungUUID = UUID.fromString("f711a38d-e792-4016-9463-286c96ce824e");

    @Test
    public void holeSaalplan() {
        // act
        var saalplan = saalplanStapel.holeSaalplan(vorstellungUUID);

        // assert
        assertThat(saalplan).isNotNull();
        assertThat(saalplan.getVorstellungUUID()).isEqualTo(vorstellungUUID);
        int reihenAnzahl = 4;
        List<ReiheNummer> reihen = saalplan.getPlaetze().keySet().stream().toList();
        assertThat(reihen).hasSize(reihenAnzahl);
        int platzAnzahlInReihe = 8;
        saalplan.getPlaetze().forEach((reihe, plaetzeListe) -> assertThat(plaetzeListe).hasSize(platzAnzahlInReihe));
    }
}