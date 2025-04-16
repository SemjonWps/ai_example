package de.wps.ddd.kino.kartenverkauf;

import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.Vorstellungen;
import de.wps.ddd.kino.kartenverkauf.domain.services.PreisService;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KartenverkaufTest {

    @Autowired
    private SaalplanStapel saalplanStapel;

    @Autowired
    private PreisService preisService;

    @Autowired
    private Vorstellungen vorstellungen;

    @Test
    void kartenverkauf() {
        var vorstellungId = UUID.fromString("95b21a30-64bf-4df1-a0a2-e769bd7c5ea1");
        var vorstellung = vorstellungen.holeVorstellung(vorstellungId);
        assertThat(vorstellung).isNotNull();

        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        assertThat(saalplan.getVorstellungUUID()).isEqualTo(vorstellungId);

        var zusammenhaengendePlaetze = saalplan.sucheZusammenhaengendePlaetze(4);
        assertThat(zusammenhaengendePlaetze.anzahl()).isEqualTo(4);

        var preis = preisService.ermittlePreis(vorstellung, zusammenhaengendePlaetze);
        assertThat(vorstellung.getEintrittspreis()).isEqualTo(Geldbetrag.euro(7, 50));
        assertThat(preis).isEqualTo(Geldbetrag.euro(30, 0));
    }

}
