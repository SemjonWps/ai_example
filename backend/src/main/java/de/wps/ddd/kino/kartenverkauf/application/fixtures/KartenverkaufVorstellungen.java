package de.wps.ddd.kino.kartenverkauf.application.fixtures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.wps.ddd.kino.common.fixtures.Fixture;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.VorstellungEntity;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories.KartenverkaufVorstellungRepository;
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
public class KartenverkaufVorstellungen implements Fixture {
    private final KartenverkaufVorstellungRepository vorstellungRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @Override
    public void install() {
        log.info("Lade Kartenverkauf-Vorstellungen aus JSON...");

        try {
            final var resource = new ClassPathResource("kartenverkauf/vorstellungen.json");
            final var vorstellungen = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<VorstellungEntity>>() {}
            );

            vorstellungRepository.saveAll(vorstellungen);
            log.info("Kartenverkauf-Vorstellungen geladen: {}", vorstellungen.size());
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Kartenverkauf-Vorstellungen aus JSON", e);
        }
    }
}
