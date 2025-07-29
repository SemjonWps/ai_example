package de.wps.ddd.kino.kartenverkauf;

import de.wps.ddd.kino.kartenverkauf.domain.factories.KartenBlock;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.Vorstellungen;
import de.wps.ddd.kino.kartenverkauf.domain.services.PreisService;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Saal;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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

    @Autowired
    private KartenBlock kinokartenblock;

    // Basiert auf Domain Story 1: Kinokarten an der Kasse verkaufen
    @Test
    void kartenverkauf() {

        // 1. Kinobesucher sucht Vorstellung in Wochenplan aus → Vorstellung ausgesucht
        var vorstellungId = new VorstellungId(UUID.fromString("090c173a-3636-4980-865a-1ec859eb4f90"));
        var vorstellung = vorstellungen.holeVorstellung(vorstellungId);
        assertThat(vorstellung.getFilmname()).isEqualTo(new Filmname("Fast and the Curious"));
        assertThat(vorstellung.getSaal()).isEqualTo(new Saal("großer Saal"));
        assertThat(vorstellung.getAnfangszeit()).isEqualTo(new Beginn(LocalDateTime.parse("2025-03-23T15:30")));

        // 2. Kinobesucher fragt nach Karten für Vorstellung → Platz-/Kartenanzahl angefragt
        var anzahlPlaetze = 4; // TODO value object?

        // 3. Kassenmitarbeiter holt Saalplan zu Vorstellung aus Saalplanstapel --> Saalplan geholt
        var saalplan = saalplanStapel.holeSaalplan(vorstellungId);
        assertThat(saalplan.getVorstellungId()).isEqualTo(vorstellungId);

        // 4. Kassenmitarbeiter sucht gewünschte Anzahl Plätze im Saalplan → Zusammenhängende Plätze gefunden
        // 5a. Kassenmitarbeiter bietet gefundene Plätze an → Plätze angeboten
        var vorgeschlagenePlaetze = saalplan.sucheZusammenhaengendePlaetze(anzahlPlaetze);
        assertThat(vorgeschlagenePlaetze.anzahl()).isEqualTo(anzahlPlaetze);
        // TODO Plätze prüfen

        // 5b. Kinobesucher stimmt den Plätzen zu → Plätzen gewählt
        var gewaehltePlaetze = new ZusammenhaengendePlaetze(vorgeschlagenePlaetze.plaetze().stream().toList());
        assertThat(gewaehltePlaetze.anzahl()).isEqualTo(anzahlPlaetze);

        // 6. Kinobesucher bezahlt Geldbetrag
        var gesamtbetrag = preisService.ermittlePreis(vorstellungId, gewaehltePlaetze);
        assertThat(vorstellung.getEintrittspreis()).isEqualTo(Geldbetrag.euro(7, 50));
        assertThat(gesamtbetrag).isEqualTo(Geldbetrag.euro(30, 0));

        // Die eigentliche Zahlung erfolgt über den ZahlungService (Zahlung-Context)

        // 7. Kassenmitarbeiter markiert verkaufte Plätze im Saalplan → Plätze als verkauft markiert
        saalplan.markiereAlsVerkauft(gewaehltePlaetze);
        assertThat(gewaehltePlaetze.plaetze()).allMatch(platzId -> saalplan.platz(platzId).istVerkauft());

        // 8. Kassenmitarbeiter legt Saalplan zurück auf Saalplanstapel → Saalplan zurückgelegt
        saalplanStapel.legeZurueck(saalplan);

        // 9. Kassenmitarbeiter beschriftet Kinokarten → Kinokarten beschriftet/erstellt/ausgestellt
        var kinokarten = kinokartenblock.erstelleKarten(vorstellung, gewaehltePlaetze);
        assertThat(kinokarten).hasSize(anzahlPlaetze);
        assertThat(kinokarten).allSatisfy(kinokarte -> {
            assertThat(kinokarte.getVorstellung()).isEqualTo(vorstellung);
            // TODO Platz prüfen
        });

        // 10. Kassenmitarbeiter übergibt fertige Kinokarten → Kinokarten übergeben/verkauft
        // TODO assertThat(KinokartenVerkauftEvent.feuert)
    }

}
