package de.wps.ddd.kino.kartenverkauf.application.fixtures;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.wps.ddd.kino.common.fixtures.Fixture;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.VorstellungEntity;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class Vorstellungen implements Fixture {

    private final KartenverkaufVorstellungRepository vorstellungRepository;

    @Transactional
    @Override
    public void install() {
        log.info("Lade Vorstellungen aus JSON...");

        try {
            var objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            var inputStream = getClass().getClassLoader().getResourceAsStream("fixtures/vorstellungen.json");
            if (inputStream == null) {
                throw new IllegalStateException("fixtures/vorstellungen.json nicht gefunden");
            }

            VorstellungEntity[] vorstellungen = objectMapper.readValue(inputStream, VorstellungEntity[].class);
            List<VorstellungEntity> vorstellungList = Arrays.asList(vorstellungen);

            vorstellungRepository.saveAll(vorstellungList);

            log.info("Vorstellungen geladen: {}", vorstellungList.size());
        } catch (IOException e) {
            throw new IllegalStateException("Fehler beim Laden der Vorstellungen", e);
        }
    }
}
