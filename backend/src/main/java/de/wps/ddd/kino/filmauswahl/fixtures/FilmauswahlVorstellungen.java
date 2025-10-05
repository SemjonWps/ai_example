package de.wps.ddd.kino.filmauswahl.fixtures;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import de.wps.ddd.kino.common.fixtures.*;
import de.wps.ddd.kino.filmauswahl.data.*;
import de.wps.ddd.kino.filmauswahl.events.*;
import java.io.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.core.io.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmauswahlVorstellungen implements Fixture {
    private final FilmauswahlVorstellungRepository vorstellungRepository;
    private final FilmauswahlSaalRepository saalRepository;
    private final ObjectMapper objectMapper;
    private final DomainEventPublisher domainEventPublisher;
    private final FilmRepository filmRepository;

    @Transactional
    @Override
    public void install() {
        log.info("Lade Filmauswahl-Säle...");

        var grosserSaal = new Saal();
        grosserSaal.setName("großer Saal");

        var kleinerSaal = new Saal();
        kleinerSaal.setName("kleiner Saal");

        saalRepository.save(grosserSaal);
        saalRepository.save(kleinerSaal);

        log.info("Filmauswahl-Säle geladen: 2");

        log.info("Lade Filmauswahl-Vorstellungen aus JSON...");

        try {
            final var resource = new ClassPathResource("filmauswahl/vorstellungen.json");
            final var vorstellungen = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<Vorstellung>>() {
                    }
            );

            vorstellungRepository.saveAll(vorstellungen);
            log.info("Filmauswahl-Vorstellungen geladen: {}", vorstellungen.size());


        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Filmauswahl-Vorstellungen aus JSON", e);
        }

        final var vorstellungen = vorstellungRepository.findAll();
        for (var vorstellung : vorstellungen) {
            Long filmId = vorstellung.getFilmId();
            final var filmTitel = filmRepository.findTitleById(filmId);

            var event = new FilmHinzugefuegtEvent(
                    vorstellung.getId(),
                    filmTitel,
                    vorstellung.getPreis(),
                    vorstellung.getSaal().getName(),
                    vorstellung.getBeginn());

            domainEventPublisher.publish(event);
        }

    }
}
