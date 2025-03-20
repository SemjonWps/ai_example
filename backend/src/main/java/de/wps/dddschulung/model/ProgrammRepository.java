package de.wps.dddschulung.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProgrammRepository {

    private final FilmRepository filmRepository;

    public List<Programmeintrag> holeProgrammeintraegefuerZeitraum(LocalDateTime start, LocalDateTime end) {
        var films = filmRepository.findFilmsBetween(start, end);
        return films.stream().map(f -> new Programmeintrag(f, f.getVorstellungen())).toList();
    }

}
