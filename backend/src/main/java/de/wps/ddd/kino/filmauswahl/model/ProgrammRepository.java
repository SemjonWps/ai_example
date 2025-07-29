package de.wps.ddd.kino.filmauswahl.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class ProgrammRepository {

    private final AktuelleFilme aktuelleFilme;

    public Programm holeProgrammFuerTag(LocalDate datum) {
        LocalDateTime start = datum.atStartOfDay();
        LocalDateTime ende = LocalTime.MAX.atDate(datum);
        var filme = aktuelleFilme.findeFilmvorstellungenZwischen(start, ende);
        return new Programm(start, ende, filme);
    }
}
