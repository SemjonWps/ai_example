package de.wps.ddd.kino.filmauswahl.service;

import de.wps.ddd.kino.common.architecture.*;
import de.wps.ddd.kino.filmauswahl.data.*;
import de.wps.ddd.kino.filmauswahl.events.*;
import de.wps.ddd.kino.filmauswahl.mapper.*;
import de.wps.ddd.kino.filmauswahl.web.*;
import java.time.*;
import java.util.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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
    private final SlotService slotService;


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

    private ArrayList<Vorstellung> generiereVorstellungen(Film film, List<Saal> saele) {
        final var random = new Random();

        final var heute = LocalDate.of(2025, 3, 19);
        final var generierteVorstellungen = new ArrayList<Vorstellung>();

        for (int tag = 0; tag < 4; tag++) {
            final var datum = heute.plusDays(tag);

            var besetzt = vorstellungRepository.findAll().stream()
                    .filter(it -> it.getBeginn().toLocalDate().equals(datum)).map(
                            it -> new TimeSlot(it.getBeginn().toLocalTime(), it.getBeginn().toLocalTime().plusMinutes(90)))
                    .toList();

            System.out.println(besetzt);

            TimeSlot timeSlot = slotService.platziereFilm(besetzt);
            var beginnZeit = timeSlot != null ? timeSlot.start : LocalTime.of(9,0);

            var beginn = LocalDateTime.of(datum, beginnZeit);

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
        return generierteVorstellungen;
    }
}
