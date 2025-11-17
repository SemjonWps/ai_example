package de.wps.ddd.kino.filmauswahl.service.domain;

import de.wps.ddd.kino.filmauswahl.data.Film;
import de.wps.ddd.kino.filmauswahl.data.FilmauswahlSaalRepository;
import de.wps.ddd.kino.filmauswahl.data.FilmauswahlVorstellungRepository;
import de.wps.ddd.kino.filmauswahl.data.Saal;
import de.wps.ddd.kino.filmauswahl.data.Vorstellung;
import de.wps.ddd.kino.filmauswahl.events.DomainEventPublisher;
import de.wps.ddd.kino.filmauswahl.events.FilmHinzugefuegtEvent;
import de.wps.ddd.kino.filmauswahl.service.ProgrammService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ProgrammServiceTest {

    @Autowired
    private ProgrammService programmService;

    @Test
    void holeVorstellungenFuerTag() {
        var datum = LocalDate.parse("2025-03-19");
        var filme = programmService.holeVorstellungenFuerTag(datum);
        assertThat(filme).isNotNull();
        assertThat(filme).hasSize(3);
        var film1 = filme.get(0);
        var film2 = filme.get(1);
        var film3 = filme.get(2);
        assertThat(film1.getTitel()).isEqualTo("Guardians of the Lunacy");
        assertThat(film2.getTitel()).isEqualTo("The Fast and the Curious");
        assertThat(film3.getTitel()).isEqualTo("Clown Wars");
        assertThat(film1.getVorstellungen()).hasSize(1);
        assertThat(film2.getVorstellungen()).hasSize(2);
        assertThat(film3.getVorstellungen()).hasSize(1);
        assertThat(film1.getVorstellungen().get(0).getBeginn()).isEqualTo("2025-03-19T14:30:00");
        assertThat(film2.getVorstellungen().get(0).getBeginn()).isEqualTo("2025-03-19T15:00:00");
        assertThat(film2.getVorstellungen().get(1).getBeginn()).isEqualTo("2025-03-19T20:30:00");
        assertThat(film3.getVorstellungen().get(0).getBeginn()).isEqualTo("2025-03-19T22:30:00");
    }
}

/**
 * Unit tests for ProgrammService methods using Mockito
 */
@ExtendWith(MockitoExtension.class)
class ProgrammServiceUnitTest {

    @Mock
    private FilmauswahlVorstellungRepository vorstellungRepository;

    @Mock
    private DomainEventPublisher domainEventPublisher;

    @InjectMocks
    private ProgrammService programmService;

    private Film testFilm;
    private List<Saal> testSaele;

    @BeforeEach
    void setUp() {
        testFilm = new Film();
        testFilm.setId(1L);
        testFilm.setTitel("Test Film");

        Saal saal1 = new Saal();
        saal1.setId(1L);
        saal1.setName("Saal 1");

        Saal saal2 = new Saal();
        saal2.setId(2L);
        saal2.setName("Saal 2");

        testSaele = Arrays.asList(saal1, saal2);
    }

    @Test
    void generiereVorstellungenFuerFilm_sollteVorstellungenGenerieren() {
        // Given
        when(vorstellungRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        programmService.generiereVorstellungenFuerFilm(testFilm, testSaele);

        // Then
        ArgumentCaptor<List<Vorstellung>> vorstellungenCaptor = ArgumentCaptor.forClass(List.class);
        verify(vorstellungRepository).saveAll(vorstellungenCaptor.capture());

        List<Vorstellung> gespeicherteVorstellungen = vorstellungenCaptor.getValue();
        assertThat(gespeicherteVorstellungen).isNotEmpty();

        // Verify that all vorstellungen have required fields
        gespeicherteVorstellungen.forEach(vorstellung -> {
            assertThat(vorstellung.getUuid()).isNotNull();
            assertThat(vorstellung.getFilmId()).isEqualTo(testFilm.getId());
            assertThat(vorstellung.getBeginn()).isNotNull();
            assertThat(vorstellung.getSaal()).isIn(testSaele);
            assertThat(vorstellung.getPreis()).isBetween(10, 15); // 10 + random(6) = 10-15
        });
    }

    @Test
    void generiereVorstellungenFuerFilm_sollteVorstellungenInKorrektemZeitraumGenerieren() {
        // Given
        when(vorstellungRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        programmService.generiereVorstellungenFuerFilm(testFilm, testSaele);

        // Then
        ArgumentCaptor<List<Vorstellung>> vorstellungenCaptor = ArgumentCaptor.forClass(List.class);
        verify(vorstellungRepository).saveAll(vorstellungenCaptor.capture());

        List<Vorstellung> gespeicherteVorstellungen = vorstellungenCaptor.getValue();

        // Verify time constraints
        LocalDate heute = LocalDate.of(2025, 3, 19);
        LocalDate maxDatum = heute.plusDays(4); // 5 days total (0-4)

        gespeicherteVorstellungen.forEach(vorstellung -> {
            LocalDateTime beginn = vorstellung.getBeginn();
            assertThat(beginn.toLocalDate()).isBetween(heute, maxDatum);
            assertThat(beginn.getHour()).isBetween(14, 21); // 14:00 - 21:xx (endStunde-1)
            assertThat(beginn.getMinute()).isIn(0, 15, 30, 45);
        });
    }

    @Test
    void generiereVorstellungenFuerFilm_sollteAngemesseneAnzahlVorstellungenGenerieren() {
        // Given
        when(vorstellungRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        programmService.generiereVorstellungenFuerFilm(testFilm, testSaele);

        // Then
        ArgumentCaptor<List<Vorstellung>> vorstellungenCaptor = ArgumentCaptor.forClass(List.class);
        verify(vorstellungRepository).saveAll(vorstellungenCaptor.capture());

        List<Vorstellung> gespeicherteVorstellungen = vorstellungenCaptor.getValue();

        // Should generate 1-3 vorstellungen per day for 5 days = 5-15 total
        assertThat(gespeicherteVorstellungen.size()).isBetween(5, 15);

        // Check distribution across days
        LocalDate heute = LocalDate.of(2025, 3, 19);
        for (int tag = 0; tag < 5; tag++) {
            LocalDate tagDatum = heute.plusDays(tag);
            long vorstellungenAmTag = gespeicherteVorstellungen.stream()
                    .filter(v -> v.getBeginn().toLocalDate().equals(tagDatum))
                    .count();
            assertThat(vorstellungenAmTag).isBetween(1L, 3L);
        }
    }

    @Test
    void generiereVorstellungenFuerFilm_sollteEventsPublizieren() {
        // Given
        when(vorstellungRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        programmService.generiereVorstellungenFuerFilm(testFilm, testSaele);

        // Then
        ArgumentCaptor<List<Vorstellung>> vorstellungenCaptor = ArgumentCaptor.forClass(List.class);
        verify(vorstellungRepository).saveAll(vorstellungenCaptor.capture());

        List<Vorstellung> gespeicherteVorstellungen = vorstellungenCaptor.getValue();

        // Verify that events were published for each vorstellung
        ArgumentCaptor<FilmHinzugefuegtEvent> eventCaptor = ArgumentCaptor.forClass(FilmHinzugefuegtEvent.class);
        verify(domainEventPublisher, times(gespeicherteVorstellungen.size())).publish(eventCaptor.capture());

        List<FilmHinzugefuegtEvent> publishedEvents = eventCaptor.getAllValues();
        assertThat(publishedEvents).hasSize(gespeicherteVorstellungen.size());

        // Verify event contents
        for (int i = 0; i < publishedEvents.size(); i++) {
            FilmHinzugefuegtEvent event = publishedEvents.get(i);
            Vorstellung vorstellung = gespeicherteVorstellungen.get(i);

            assertThat(event.getUuid()).isEqualTo(vorstellung.getUuid());
            assertThat(event.getTitel()).isEqualTo(testFilm.getTitel());
            assertThat(event.getPreis()).isEqualTo(vorstellung.getPreis());
            assertThat(event.getSaal()).isEqualTo(vorstellung.getSaal().getName());
            assertThat(event.getBeginn()).isEqualTo(vorstellung.getBeginn());
        }
    }

    @Test
    void generiereVorstellungenFuerFilm_mitLeerenSaelen_sollteNichtsGenerieren() {
        // Given
        List<Saal> leereSaele = Arrays.asList();

        // When & Then - Should not throw exception and not call repository
        programmService.generiereVorstellungenFuerFilm(testFilm, leereSaele);

        // Verify no interactions with repositories
        verify(vorstellungRepository, times(0)).saveAll(anyList());
        verify(domainEventPublisher, times(0)).publish(any());
    }

    @Test
    void generiereVorstellungenFuerFilm_sollteUniqueUuidsGenerieren() {
        // Given
        when(vorstellungRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        programmService.generiereVorstellungenFuerFilm(testFilm, testSaele);

        // Then
        ArgumentCaptor<List<Vorstellung>> vorstellungenCaptor = ArgumentCaptor.forClass(List.class);
        verify(vorstellungRepository).saveAll(vorstellungenCaptor.capture());

        List<Vorstellung> gespeicherteVorstellungen = vorstellungenCaptor.getValue();

        // Extract all UUIDs and verify uniqueness
        List<String> uuids = gespeicherteVorstellungen.stream()
                .map(v -> v.getUuid().toString())
                .toList();

        assertThat(uuids).doesNotHaveDuplicates();
    }
}

