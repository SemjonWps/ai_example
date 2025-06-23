package de.wps.ddd.kino.filmauswahl.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class ProgrammRepository {

    private final FilmRepository filmRepository;

    public Programm holeProgrammFuerTag(LocalDate datum) {
        LocalDateTime start = datum.atStartOfDay();
        LocalDateTime ende = LocalTime.MAX.atDate(datum);
        var filme = filmRepository.findFilmsBetween(start, ende);
        var programmeintraege = filme.stream().map(f -> new Programmeintrag(f, f.getVorstellungen())).toList();
        return new Programm(start, ende, programmeintraege);
    }
}
