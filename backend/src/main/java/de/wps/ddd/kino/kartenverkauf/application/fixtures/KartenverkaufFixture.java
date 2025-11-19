package de.wps.ddd.kino.kartenverkauf.application.fixtures;

import de.wps.ddd.kino.common.fixtures.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.SaalEntity;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories.*;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class KartenverkaufFixture implements Fixture {

    private final AktuelleVorstellungen aktuelleVorstellungen;
    private final SaalplanStapel saalplanStapel;
    private final KartenverkaufVorstellungRepository vorstellungRepository;
    private final SaalRepository saalRepository;
    private boolean areSaeleInstalled = false;

    @Transactional
    @Override
    public void install() {
        // Installation happens lazily in ensureSaeleInstalled()
    }

    @Transactional
    public void ensureSaeleInstalled() {
        if (!areSaeleInstalled) {
            installSaele();
        }
    }

    private void installSaele() {
        log.info("Lade Säle...");

        saalRepository.save(new SaalEntity("großer Saal", 6, 20));
        saalRepository.save(new SaalEntity("kleiner Saal", 4, 8));

        log.info("Säle geladen: {}", saalRepository.count());

        log.info("Lade Vorstellungen aus JSON...");
        areSaeleInstalled = true;
    }
}
