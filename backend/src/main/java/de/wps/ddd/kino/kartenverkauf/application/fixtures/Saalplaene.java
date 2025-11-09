package de.wps.ddd.kino.kartenverkauf.application.fixtures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.wps.ddd.kino.common.fixtures.Fixture;
import de.wps.ddd.kino.filmauswahl.events.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories.*;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.*;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.AktuelleVorstellungen;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalKonfiguration;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class Saalplaene implements Fixture {

    private final AktuelleVorstellungen aktuelleVorstellungen;
    private final SaalplanStapel saalplanStapel;
    private final SaalKonfiguration saalKonfiguration;
    private final KartenverkaufVorstellungRepository vorstellungRepository;
    private final ObjectMapper objectMapper;
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

    @EventListener
    @Transactional
    public void handleFilmAktualisiert(FilmHinzugefuegtEvent event) {
        log.info("Empfange FilmHinzugefuegtEvent: {}", event);
        generiereVorstellungenFuerNaechsteFuenfTage(event);
    }

    private void generiereVorstellungenFuerNaechsteFuenfTage(FilmHinzugefuegtEvent event) {
        log.info("Generiere Vorstellungen für Film '{}' für die nächsten 5 Tage", event.getTitel());

        var random = new Random();
        var saele = new String[]{"großer Saal", "kleiner Saal"};
        var zeitslots = new LocalTime[]{
            LocalTime.of(14, 30),  // Nachmittag
            LocalTime.of(19, 30)   // Abend
        };

        var heute = LocalDate.now();
        var generierteVorstellungen = new ArrayList<VorstellungEntity>();

        // Generiere für die nächsten 5 Tage
        for (int tag = 0; tag < 5; tag++) {
            var datum = heute.plusDays(tag);

            // Generiere 1-2 Vorstellungen pro Tag
            var anzahlVorstellungenProTag = 1 + random.nextInt(2); // 1 oder 2

            for (int i = 0; i < anzahlVorstellungenProTag; i++) {
                var zeitslot = zeitslots[i % zeitslots.length];
                var beginn = LocalDateTime.of(datum, zeitslot);
                var saal = saele[random.nextInt(saele.length)];

                var vorstellung = new VorstellungEntity(
                    UUID.randomUUID(),
                    beginn,
                    saal,
                    event.getTitel(),
                    event.getPreis()
                );

                generierteVorstellungen.add(vorstellung);
            }
        }

        // Speichere alle generierten Vorstellungen
        vorstellungRepository.saveAll(generierteVorstellungen);
        log.info("Vorstellungen generiert und gespeichert: {}", generierteVorstellungen.size());

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
}
