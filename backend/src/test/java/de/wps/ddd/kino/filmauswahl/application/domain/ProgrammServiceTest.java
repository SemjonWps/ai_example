package de.wps.ddd.kino.filmauswahl.application.domain;

import de.wps.ddd.kino.filmauswahl.application.ProgrammService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ProgrammServiceTest {

    @Autowired
    private ProgrammService programmService;

    @Test
    void holeVorstellungenFuerTag() {
        var datum = LocalDate.parse("2025-03-19");
        var filme = programmService.holeVorstellungenFuerTag(datum);
        assertThat(filme).isNotNull();
        assertThat(filme).hasSize(2);
        var film1 = filme.get(0);
        var film2 = filme.get(1);
        assertThat(film1.getTitel()).isEqualTo("Space Farce");
        assertThat(film2.getTitel()).isEqualTo("Back to the Futura");
        assertThat(film1.getVorstellungen()).hasSize(1);
        assertThat(film2.getVorstellungen()).hasSize(2);
        assertThat(film1.getVorstellungen().get(0).getBeginn()).isEqualTo("2025-03-19T14:30:00");
        assertThat(film2.getVorstellungen().get(0).getBeginn()).isEqualTo("2025-03-19T15:30:00");
        assertThat(film2.getVorstellungen().get(1).getBeginn()).isEqualTo("2025-03-19T20:30:00");
    }
}
