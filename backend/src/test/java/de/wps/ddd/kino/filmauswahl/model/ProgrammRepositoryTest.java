package de.wps.ddd.kino.filmauswahl.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ProgrammRepositoryTest {

    @Autowired
    private ProgrammRepository programmRepository;

    @Test
    void holeProgrammFuerTag() {
        var datum = LocalDate.parse("2025-03-19");
        var programm = programmRepository.holeProgrammFuerTag(datum);
        assertThat(programm).isNotNull();
        assertThat(programm.getFilmvorstellungen()).hasSize(2);
        var film1 = programm.getFilmvorstellungen().get(0);
        var film2 = programm.getFilmvorstellungen().get(1);
        assertThat(film1.getTitel()).isEqualTo("Space Farce");
        assertThat(film2.getTitel()).isEqualTo("Back to the Futura");
        assertThat(film1.getVorstellungen()).hasSize(1);
        assertThat(film2.getVorstellungen()).hasSize(2);
        assertThat(film1.getVorstellungen().get(0).getAnfangszeit()).isEqualTo("2025-03-19T14:30:00");
        assertThat(film2.getVorstellungen().get(0).getAnfangszeit()).isEqualTo("2025-03-19T15:30:00");
        assertThat(film2.getVorstellungen().get(1).getAnfangszeit()).isEqualTo("2025-03-19T20:30:00");
    }
}
