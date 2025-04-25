package de.wps.ddd.kino.kartenverkauf;

import de.wps.ddd.kino.kartenverkauf.domain.factories.KartenBlock;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.Vorstellungen;
import de.wps.ddd.kino.kartenverkauf.domain.services.BezahlService;
import de.wps.ddd.kino.kartenverkauf.domain.services.PreisService;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsanforderung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsbestaetigung;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class KartenverkaufTest {

    @Autowired
    private Vorstellungen vorstellungen;

    @Autowired
    private SaalplanStapel saalplanStapel;

    @Autowired
    private PreisService preisService;

    private BezahlService bezahlService; // TODO

    @Autowired
    private KartenBlock kinokartenblock;

    @Test
    void kartenverkauf() {
        var vorstellungId = UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90");
        var vorstellung = vorstellungen.holeVorstellung(vorstellungId);
        assertThat(vorstellung).isNotNull();

        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        assertThat(saalplan.getVorstellungUUID()).isEqualTo(vorstellungId);

        var vorgeschlagenePlaetze = saalplan.sucheZusammenhaengendePlaetze(4);
        assertThat(vorgeschlagenePlaetze.anzahl()).isEqualTo(4);

        var gewaehltePlaetze = vorgeschlagenePlaetze;
        assertThat(gewaehltePlaetze.anzahl()).isEqualTo(4);

        var gesamtpreis = preisService.ermittlePreis(vorstellungId, gewaehltePlaetze);
        assertThat(vorstellung.getEintrittspreis()).isEqualTo(Geldbetrag.euro(7, 50));
        assertThat(gesamtpreis).isEqualTo(Geldbetrag.euro(30, 0));

        var zahlungsanforderung = new Zahlungsanforderung(vorstellung, gewaehltePlaetze, gesamtpreis);
        assertThat(zahlungsanforderung.betrag()).isEqualTo(Geldbetrag.euro(30, 0));

        var zahlungsbestaetigung = new Zahlungsbestaetigung(zahlungsanforderung, Zahlungsbestaetigung.Status.BEZAHLT);

        saalplan.markiereAlsVerkauft(gewaehltePlaetze);
        // TODO assert verkauft

        var kinokarten = kinokartenblock.erstelleKarten(vorstellung, gewaehltePlaetze);
        assertThat(kinokarten).hasSize(4);
        // TODO karteninhalt prüfen

        // TODO assertThat(KinokartenVerkauftEvent.feuert)
    }

}
