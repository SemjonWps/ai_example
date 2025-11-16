package de.wps.ddd.kino.filmauswahl.fixtures;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import de.wps.ddd.kino.common.fixtures.*;
import de.wps.ddd.kino.filmauswahl.data.*;
import de.wps.ddd.kino.filmauswahl.events.*;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.event.mappers.*;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.*;
import java.io.*;
import java.time.*;
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
        var saele = installSaele();

        var filme = installFilme();

        installVorstellungen(saele, filme);
    }

    private List<Saal> installSaele() {
        log.info("Lade Filmauswahl-Säle...");

        var grosserSaal = new Saal();
        grosserSaal.setName("großer Saal");

        var kleinerSaal = new Saal();
        kleinerSaal.setName("kleiner Saal");

        var saele = List.of(grosserSaal, kleinerSaal);
        saalRepository.saveAll(saele);

        log.info("Filmauswahl-Säle geladen: 2");

        return saele;
    }

    private List<Film> installFilme() {
        log.info("Lade Filmauswahl-Filme aus JSON...");

        try {
            final var resource = new ClassPathResource("filmauswahl/filme.json");
            final var filme = objectMapper.readValue(
                    resource.getInputStream(),
                    new TypeReference<List<Film>>() {
                    }
            );

            filmRepository.saveAll(filme);
            log.info("Filmauswahl-Filme geladen: {}", filme.size());

            return filme;

        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Laden der Filmauswahl-Filme aus JSON", e);
        }
    }

    private void installVorstellungen(List<Saal> saele, List<Film> filme) {
        log.info("Lade Filmauswahl-Vorstellungen aus JSON...");

        log.info("Generiere Vorstellungen für Film '{}' für die nächsten 5 Tage", event.getTitel());

        var random = new Random(42);
        var saele = new String[]{ "großer Saal", "kleiner Saal" };

        // Zeitraum: 14:00 bis 22:00 (2pm bis 10pm)
        var startStunde = 14;
        var endStunde = 22;
        var moeglicheMinuten = new int[]{ 0, 15, 30, 45 };

        var heute = LocalDate.of(2025, 3, 19);
        var generierteVorstellungen = new ArrayList<VorstellungEntity>();

        // Generiere für die nächsten 5 Tage
        for (int tag = 0; tag < 5; tag++) {
            var datum = heute.plusDays(tag);

            // Generiere 1-2 Vorstellungen pro Tag
            var anzahlVorstellungenProTag = 1 + random.nextInt(2); // 1 oder 2

            for (int i = 0; i < anzahlVorstellungenProTag; i++) {
                // Zufällige Stunde zwischen 14 und 21 (inclusive)
                var stunde = startStunde + random.nextInt(endStunde - startStunde);
                // Zufällige Minuten: 0, 15, 30 oder 45
                var minute = moeglicheMinuten[random.nextInt(moeglicheMinuten.length)];
                var zeitslot = LocalTime.of(stunde, minute);

                var beginn = LocalDateTime.of(datum, zeitslot);
                var saal = saele[random.nextInt(saele.length)];

                var vorstellung = FilmHinzugefuegtEventMapper.map(event, beginn, saal);

                generierteVorstellungen.add(vorstellung);
            }
        }

        vorstellungRepository.saveAll(vorstellungen);
        log.info("Filmauswahl-Vorstellungen geladen: {}", vorstellungen.size());


        final var vorstellungen = vorstellungRepository.findAll();
        for (var vorstellung : vorstellungen) {
            Long filmId = vorstellung.getFilmId();
            final var filmTitel = filmRepository.findTitleById(filmId);

            var event = new FilmHinzugefuegtEvent(
                    vorstellung.getUuid(),
                    filmTitel,
                    vorstellung.getPreis(),
                    vorstellung.getSaal().getName(),
                    vorstellung.getBeginn());

            domainEventPublisher.publish(event);
        }
    }
}
