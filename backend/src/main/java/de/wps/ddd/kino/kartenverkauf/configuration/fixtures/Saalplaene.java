package de.wps.ddd.kino.kartenverkauf.configuration.fixtures;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.SaalplanEntity;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories.SaalplanRepository;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories.VorstellungRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
// Spring Boot will automatically call the run method of all beans implementing this interface after the application context has been loaded.
public class Saalplaene implements CommandLineRunner {

    private final VorstellungRepository vorstellungRepository;
    private final SaalplanRepository saalplanRepository;

    @Override
    public void run(String... args) {
        log.info("Erzeuge Saalpläne...");

        var random = new Random(42);

        var vorstellungen = vorstellungRepository.findAll();
        for (var vorstellung : vorstellungen) {
            log.info("Erzeuge Saalplan für Vorstellung: {}", vorstellung);

            var reihen = switch (vorstellung.getSaal()) {
                case "kleiner Saal" -> 4;
                case "großer Saal" -> 6;
                default -> throw new IllegalStateException("Unexpected value: " + vorstellung.getSaal());
            };

            var spalten = switch (vorstellung.getSaal()) {
                case "kleiner Saal" -> 8;
                case "großer Saal" -> 12;
                default -> throw new IllegalStateException("Unexpected value: " + vorstellung.getSaal());
            };

            var saalplanEntity = new SaalplanEntity(null, vorstellung.getUuid(), new ArrayList<>());
            saalplanEntity = saalplanRepository.save(saalplanEntity); // generates ID

            var plaetze = new ArrayList<PlatzEntity>(reihen * spalten);
            for (int reihe = 1; reihe <= reihen; reihe++) {
                for (int spalte = 1; spalte <= spalten; spalte++) {
                    var istVerkauft = random.nextInt(4) == 0;
                    var platz = new PlatzEntity(new PlatzEntity.Id(saalplanEntity.getId(), reihe, spalte), istVerkauft, null);
                    plaetze.add(platz);
                }
            }

            saalplanEntity.setPlaetze(plaetze);
            saalplanRepository.save(saalplanEntity);
        }

        log.info("Saalpläne erzeugt: {}", saalplanRepository.count());
    }
}
