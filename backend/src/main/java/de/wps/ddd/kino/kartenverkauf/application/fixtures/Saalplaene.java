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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        try {
            final var resource = new ClassPathResource("kartenverkauf/vorstellungen.json");
            final var vorstellungen = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<VorstellungEntity>>() {}
            );

            vorstellungRepository.saveAll(vorstellungen);
            log.info("Vorstellungen geladen: {}", vorstellungen.size());
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Vorstellungen aus JSON", e);
        }

        log.info("Erzeuge Saalpläne...");

        var random = new Random(42);

        var vorstellungen = aktuelleVorstellungen.alleVorstellungen();
        for (var vorstellung : vorstellungen) {
            initialisiereVorstellung(vorstellung, random);
        }

        log.info("Saalpläne erzeugt: {}", vorstellungen.size());
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
    public void handleFilmAktualisiert(FilmHinzugefuegtEvent event) {
        log.info("Empfange FilmHinzugefuegtEvent: {}", event);
    }
}
