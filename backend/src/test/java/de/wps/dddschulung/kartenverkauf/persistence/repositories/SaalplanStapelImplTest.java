package de.wps.dddschulung.kartenverkauf.persistence.repositories;


import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
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

    @Test
    public void holeSaalplan() {
        // arrange
        UUID vorstellungUUID = UUID.fromString("95b21a30-64bf-4df1-a0a2-e769bd7c5ea1");

        // act
        Saalplan saalplan = saalplanStapel.holeSaalplan(vorstellungUUID);

        // assert
        assertThat(saalplan.getVorstellungUUID()).isEqualTo(vorstellungUUID);
        int reihenAnzahl = 4;
        List<Reihe> reihen = saalplan.getPlaetze().keySet().stream().toList();
        assertThat(reihen).hasSize(reihenAnzahl);
        int platzAnzahlInReihe = 12;
        saalplan.getPlaetze().forEach((reihe, plaetzeListe) -> assertThat(plaetzeListe).hasSize(platzAnzahlInReihe));
    }

}