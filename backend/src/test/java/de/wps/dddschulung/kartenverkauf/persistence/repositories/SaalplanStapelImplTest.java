package de.wps.dddschulung.kartenverkauf.persistence.repositories;


import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
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

    Saalplan saalplan;
    UUID vorstellungUUID = UUID.fromString("95b21a30-64bf-4df1-a0a2-e769bd7c5ea1");

    @Test
    public void holeSaalplan() {
        // act
        saalplan = saalplanStapel.holeSaalplan(vorstellungUUID);

        // assert
        assertThat(saalplan.getVorstellungUUID()).isEqualTo(vorstellungUUID);
        int reihenAnzahl = 4;
        List<Reihe> reihen = saalplan.getPlaetze().keySet().stream().toList();
        assertThat(reihen).hasSize(reihenAnzahl);
        int platzAnzahlInReihe = 12;
        saalplan.getPlaetze().forEach((reihe, plaetzeListe) -> assertThat(plaetzeListe).hasSize(platzAnzahlInReihe));
    }

    @Test
    public void legeZurueck() {
        // arrange
        saalplan = saalplanStapel.holeSaalplan(vorstellungUUID);
        Reihe reihe = new Reihe(1);

        // act
        Platz zuAendernderPlatz = saalplan.getPlaetze().get(reihe).getFirst();
        zuAendernderPlatz.markiereAlsVerkauft();
        saalplanStapel.legeZurueck(saalplan);

        // TODO assert??
    }

}