package de.wps.ddd.kino.filmauswahl.fixtures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.wps.ddd.kino.common.fixtures.Fixture;
import de.wps.ddd.kino.filmauswahl.data.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmauswahlVorstellungen implements Fixture {
    private final FilmauswahlVorstellungRepository vorstellungRepository;
    private final FilmauswahlSaalRepository saalRepository;
    private final ObjectMapper objectMapper;

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
                new TypeReference<List<Vorstellung>>() {}
            );

            vorstellungRepository.saveAll(vorstellungen);
            log.info("Filmauswahl-Vorstellungen geladen: {}", vorstellungen.size());


        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Filmauswahl-Vorstellungen aus JSON", e);
        }
    }
}
