package de.wps.ddd.kino.filmauswahl.service;

import de.wps.ddd.kino.common.architecture.ApplicationService;
import de.wps.ddd.kino.filmauswahl.data.AktuelleFilme;
import de.wps.ddd.kino.filmauswahl.data.Film;
import de.wps.ddd.kino.filmauswahl.data.FilmRepository;
import de.wps.ddd.kino.filmauswahl.data.FilmauswahlSaalRepository;
import de.wps.ddd.kino.filmauswahl.data.FilmauswahlVorstellungRepository;
import de.wps.ddd.kino.filmauswahl.data.Saal;
import de.wps.ddd.kino.filmauswahl.data.Vorstellung;
import de.wps.ddd.kino.filmauswahl.events.*;
import de.wps.ddd.kino.filmauswahl.mapper.FilmMapper;
import de.wps.ddd.kino.filmauswahl.web.FilmEingebenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@ApplicationService
@Service
@RequiredArgsConstructor
@Slf4j
public class ProgrammService {

    public static final int MAX_ANZAHL_VORSTELLUNGEN_PRO_TAG = 2;
    private final AktuelleFilme aktuelleFilme;
    private final FilmRepository filmRepository;
    private final FilmauswahlSaalRepository saalRepository;
    private final FilmauswahlVorstellungRepository vorstellungRepository;
    private final DomainEventPublisher domainEventPublisher;
    private final FilmMapper filmMapper;

    public List<Film> holeVorstellungenFuerTag(LocalDate datum) {
        LocalDateTime start = datum.atStartOfDay();
        LocalDateTime ende = LocalTime.MAX.atDate(datum);
        return aktuelleFilme.findeVorstellungenZwischen(start, ende);
    }

    @Transactional
    public Film fuegeFilmHinzu(FilmEingebenDto filmDto) {
        log.info("Füge neuen Film hinzu: {}", filmDto.getTitel());

        Film film = filmMapper.toEntity(filmDto);

        // Save film
        film = filmRepository.save(film);
        log.info("Film gespeichert mit ID: {}", film.getId());

        // Generate vorstellungen for this film
        List<Saal> saele = saalRepository.findAll();
        generiereVorstellungenFuerFilm(film, saele);

        return film;
    }

    public void generiereVorstellungenFuerFilm(Film film, List<Saal> saele) {
        log.info("Generiere Vorstellungen für Film: {}", film.getTitel());

        final var generierteVorstellungen = generiereVorstellungen(film, saele);

        vorstellungRepository.saveAll(generierteVorstellungen);
        log.info("Vorstellungen für Film '{}' generiert: {}", film.getTitel(), generierteVorstellungen.size());

        // Publish domain events
        for (var vorstellung : generierteVorstellungen) {
            var event = new FilmHinzugefuegtEvent(
                    vorstellung.getUuid(),
                    film.getTitel(),
                    vorstellung.getPreis(),
                    vorstellung.getSaal().getName(),
                    vorstellung.getBeginn());

            domainEventPublisher.publish(event);
        }
    }

    private static ArrayList<Vorstellung> generiereVorstellungen(Film film, List<Saal> saele) {
        final var random = new Random();

        final var startStunde = 14;
        final var endStunde = 22;
        final var moeglicheMinuten = new int[]{ 0, 15, 30, 45 };

        final var heute = LocalDate.of(2025, 3, 19);
        final var generierteVorstellungen = new ArrayList<Vorstellung>();

        for (int tag = 0; tag < 4; tag++) {
            var datum = heute.plusDays(tag);

            var anzahlVorstellungenProTag = 1 + random.nextInt(MAX_ANZAHL_VORSTELLUNGEN_PRO_TAG);

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
        return generierteVorstellungen;
    }
}
