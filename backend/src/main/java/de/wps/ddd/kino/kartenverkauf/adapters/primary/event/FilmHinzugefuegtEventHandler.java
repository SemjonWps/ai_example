package de.wps.ddd.kino.kartenverkauf.adapters.primary.event;

import de.wps.ddd.kino.filmauswahl.events.FilmHinzugefuegtEvent;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.event.mappers.FilmHinzugefuegtEventMapper;
import de.wps.ddd.kino.kartenverkauf.application.fixtures.KartenverkaufFixture;
import de.wps.ddd.kino.kartenverkauf.application.ports.primary.VorstellungsInitialisierung;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.AktuelleVorstellungen;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmHinzugefuegtEventHandler {

    private final VorstellungsInitialisierung vorstellungsInitialisierung;
    private final AktuelleVorstellungen aktuelleVorstellungen;
    private final KartenverkaufFixture kartenverkaufFixture;

    @EventListener
    @Transactional
    public void handleFilmAktualisiert(FilmHinzugefuegtEvent event) {
        // Lazy initialization: Install Saele on first event
        kartenverkaufFixture.ensureSaeleInstalled();

        log.info("Empfange FilmHinzugefuegtEvent: {}", event);

        var neueVorstellung = FilmHinzugefuegtEventMapper.map(event);
        aktuelleVorstellungen.hinzufuegen(neueVorstellung);

        // Initialisiere Saalpläne für alle Vorstellungen mit diesem Film
        var alleVorstellungen = aktuelleVorstellungen.alleVorstellungen();
        for (var vorstellung : alleVorstellungen) {
            if (vorstellung.getFilm().name().equals(event.getTitel())) {
                vorstellungsInitialisierung.initialisiereSaalplan(vorstellung);
            }
        }

        log.info("Saalpläne für Vorstellungen mit Film '{}' initialisiert", event.getTitel());
    }
}
