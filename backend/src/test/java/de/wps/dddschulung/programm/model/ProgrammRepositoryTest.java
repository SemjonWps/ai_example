package de.wps.dddschulung.programm.model;

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
        assertThat(programm.getProgrammeintraege()).hasSize(2);
        var p1 = programm.getProgrammeintraege().get(0);
        var p2 = programm.getProgrammeintraege().get(1);
        assertThat(p1.getFilm().getTitel()).isEqualTo("Space Farce");
        assertThat(p2.getFilm().getTitel()).isEqualTo("Back to the Futura");
        assertThat(p1.getVorstellungen()).hasSize(1);
        assertThat(p2.getVorstellungen()).hasSize(2);
        assertThat(p1.getVorstellungen().get(0).getAnfangszeit()).isEqualTo("2025-03-19T14:30:00");
        assertThat(p2.getVorstellungen().get(0).getAnfangszeit()).isEqualTo("2025-03-19T15:30:00");
        assertThat(p2.getVorstellungen().get(1).getAnfangszeit()).isEqualTo("2025-03-19T20:30:00");
    }
}
