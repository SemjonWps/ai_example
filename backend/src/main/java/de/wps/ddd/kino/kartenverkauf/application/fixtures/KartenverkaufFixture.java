package de.wps.ddd.kino.kartenverkauf.application.fixtures;

import de.wps.ddd.kino.common.fixtures.*;
import de.wps.ddd.kino.filmauswahl.events.*;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.event.mappers.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories.*;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.*;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.*;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.*;
import java.time.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class KartenverkaufFixture implements Fixture {

    private final AktuelleVorstellungen aktuelleVorstellungen;
    private final SaalplanStapel saalplanStapel;
    private final SaalKonfiguration saalKonfiguration;
    private final KartenverkaufVorstellungRepository vorstellungRepository;
    private final SaalRepository saalRepository;

    @Transactional
    @Override
    public void install() {
        installSaele();
        installVorstellungen();
    }

    private void installVorstellungen() {
        // Vorstellungen werden nicht mehr aus JSON geladen, sondern
        // programmatisch generiert, wenn FilmHinzugefuegtEvent empfangen wird
        log.info("Überspringe JSON-basiertes Laden von Vorstellungen - werden durch Events generiert");

        // Falls bereits Vorstellungen existieren (z.B. aus Events), initialisiere deren Saalpläne
        var vorstellungen = aktuelleVorstellungen.alleVorstellungen();
        if (!vorstellungen.isEmpty()) {
            log.info("Erzeuge Saalpläne für existierende Vorstellungen...");
            var random = new Random(42);

            for (var vorstellung : vorstellungen) {
                initialisiereVorstellung(vorstellung, random);
            }

            log.info("Vorstellung erzeugt: {}", vorstellungen.size());
        } else {
            log.info("Keine Vorstellungen zum Initialisieren gefunden");
        }
    }

    private void installSaele() {
        log.info("Lade Säle...");

        saalRepository.save(new SaalEntity("großer Saal", 6, 20));
        saalRepository.save(new SaalEntity("kleiner Saal", 4, 8));

        log.info("Säle geladen: {}", saalRepository.count());


        log.info("Lade Vorstellungen aus JSON...");
    }

    @EventListener
    @Transactional
    public void handleFilmAktualisiert(FilmHinzugefuegtEvent event) {
        log.info("Empfange FilmHinzugefuegtEvent: {}", event);
        generiereVorstellungenFuerNaechsteFuenfTage(event);
    }

    private void generiereVorstellungenFuerNaechsteFuenfTage(FilmHinzugefuegtEvent event) {
        log.info("Generiere Vorstellungen für Film '{}' für die nächsten 5 Tage", event.getTitel());

        var vorstellung = FilmHinzugefuegtEventMapper.map(event);

        // Speichere alle generierten Vorstellungen
        vorstellungRepository.save(vorstellung);
        log.info("Vorstellung gespeichert: {}");

        // Initialisiere Saalpläne für alle generierten Vorstellungen
        var alleVorstellungen = aktuelleVorstellungen.alleVorstellungen();
        var randomFuerSaalplaene = new Random(42);

        for (var vorstellung : alleVorstellungen) {
            if (vorstellung.getFilm().equals(event.getTitel())) {
                initialisiereVorstellung(vorstellung, randomFuerSaalplaene);
            }
        }

        log.info("Saalpläne für {} Vorstellungen initialisiert", generierteVorstellungen.size());
    }

    private void initialisiereVorstellung(Vorstellung vorstellung, Random random) {
        log.info("Erzeuge Saalplan für Vorstellung: {}", vorstellung);

        var abmessungen = saalKonfiguration.findeAbmessungen(vorstellung.getSaal())
                .orElseThrow(() -> new IllegalStateException("Keine Konfiguration gefunden für Saal: " + vorstellung.getSaal().name()));

        var reihen = abmessungen.reihen();
        var spalten = abmessungen.spalten();

        var plaetze = new ArrayList<Platz>(reihen * spalten);
        for (int reihe = 1; reihe <= reihen; reihe++) {
            for (int spalte = 1; spalte <= spalten; spalte++) {
                var istVerkauft = random.nextInt(4) == 0;
                var platz = new Platz(new PlatzId(new ReiheNummer(reihe), new PlatzNummer(spalte)), istVerkauft, null);
                plaetze.add(platz);
            }
        }

        var saalplan = new Saalplan(vorstellung.getId(), plaetze);

        saalplanStapel.legeZurueck(saalplan);
    }
}
