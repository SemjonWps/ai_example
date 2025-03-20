package de.wps.dddschulung.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class ProgrammRepositoryTest {
    @Autowired
    private ProgrammRepository programmRepository;


    @Test
    void holeVorstellungenfuerZeitraum() {
        var start = LocalDateTime.parse("2025-03-19T00:00:00");
        var ende = LocalDateTime.parse("2025-03-20T00:00:00");
        var result = programmRepository.holeProgrammeintraegefuerZeitraum(start, ende);
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        var p1 = result.get(0);
        var p2 = result.get(1);
        assertThat(p1.getFilm().getTitel()).isEqualTo("Space Farce");
        assertThat(p2.getFilm().getTitel()).isEqualTo("Back to the Futura");
        assertThat(p1.getVorstellungen()).hasSize(1);
        assertThat(p2.getVorstellungen()).hasSize(2);
        assertThat(p1.getVorstellungen().get(0).getAnfangszeit()).isEqualTo("2025-03-19T14:30:00");
        assertThat(p2.getVorstellungen().get(0).getAnfangszeit()).isEqualTo("2025-03-19T15:30:00");
        assertThat(p2.getVorstellungen().get(1).getAnfangszeit()).isEqualTo("2025-03-19T20:30:00");
    }
}
