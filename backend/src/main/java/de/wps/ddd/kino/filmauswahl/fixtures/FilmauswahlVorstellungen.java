package de.wps.ddd.kino.filmauswahl.fixtures;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import de.wps.ddd.kino.common.fixtures.*;
import de.wps.ddd.kino.filmauswahl.data.*;
import de.wps.ddd.kino.filmauswahl.service.*;
import java.io.*;
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
    private final FilmauswahlSaalRepository saalRepository;
    private final ObjectMapper objectMapper;
    private final FilmRepository filmRepository;
    private final ProgrammService programmService;

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

        for (var film : filme) {
            programmService.generiereVorstellungenFuerFilm(film, saele);
        }

        log.info("Filmauswahl-Vorstellungen geladen");
    }
}
