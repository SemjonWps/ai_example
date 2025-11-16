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
    public static final int MAXIMUM_NUMBER_OF_VORSTELLUNGEN_PER_DAY = 3;
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
        log.info("Generiere Vorstellungen für die nächsten 5 Tage");

        final var random = new Random(42);

        // Zeitraum: 14:00 bis 22:00 (2pm bis 10pm)
        final var startStunde = 14;
        final var endStunde = 22;
        final var moeglicheMinuten = new int[]{ 0, 15, 30, 45 };

        final var heute = LocalDate.of(2025, 3, 19);
        final var generierteVorstellungen = new ArrayList<Vorstellung>();

        for (var film : filme) {

            // Generiere für die nächsten 5 Tage
            for (int tag = 0; tag < 5; tag++) {
                var datum = heute.plusDays(tag);

                // Generiere 1-2 Vorstellungen pro Tag
                var anzahlVorstellungenProTag = 1 + random.nextInt(MAXIMUM_NUMBER_OF_VORSTELLUNGEN_PER_DAY); // 1 oder 2

                for (int i = 0; i < anzahlVorstellungenProTag; i++) {
                    // Zufällige Stunde zwischen 14 und 21 (inclusive)
                    var stunde = startStunde + random.nextInt(endStunde - startStunde);
                    // Zufällige Minuten: 0, 15, 30 oder 45
                    var minute = moeglicheMinuten[random.nextInt(moeglicheMinuten.length)];
                    var zeitslot = LocalTime.of(stunde, minute);

                    var beginn = LocalDateTime.of(datum, zeitslot);
                    var saal = saele.get(random.nextInt(saele.size()));

                    final var eintrittspreis = 10 + random.nextInt(6);
                    var vorstellung = new Vorstellung();
                    vorstellung.setUuid(UUID.randomUUID());
                    vorstellung.setBeginn(beginn);
                    vorstellung.setSaal(saal);
                    vorstellung.setFilmId(film.getId());
                    vorstellung.setPreis(eintrittspreis);

                    generierteVorstellungen.add(vorstellung);
                }
            }
        }

        vorstellungRepository.saveAll(generierteVorstellungen);
        log.info("Filmauswahl-Vorstellungen geladen: {}", generierteVorstellungen.size());


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
