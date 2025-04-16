package de.wps.ddd.kino.kartenverkauf.persistence.repositories;


import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
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

    Saalplan saalplan;
    UUID vorstellungUUID = UUID.fromString("95b21a30-64bf-4df1-a0a2-e769bd7c5ea1");

    @Test
    public void holeSaalplan() {
        // act
        saalplan = saalplanStapel.holeSaalplan(vorstellungUUID);

        // assert
        assertThat(saalplan.getVorstellungUUID()).isEqualTo(vorstellungUUID);
        int reihenAnzahl = 4;
        List<Reihennummer> reihen = saalplan.getPlaetze().keySet().stream().toList();
        assertThat(reihen).hasSize(reihenAnzahl);
        int platzAnzahlInReihe = 12;
        saalplan.getPlaetze().forEach((reihe, plaetzeListe) -> assertThat(plaetzeListe).hasSize(platzAnzahlInReihe));
    }
}