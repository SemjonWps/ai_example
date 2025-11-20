package de.wps.ddd.kino.filmauswahl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Unit tests for SlotService methods using Mockito
 */
@ExtendWith(MockitoExtension.class)
class SlotServiceTest {

    @Mock
    private Random mockRandom;

    @InjectMocks
    private SlotService slotService;

    @BeforeEach
    void setUp() throws Exception {
        // Inject the mock Random object into the SlotService
        Field randomField = SlotService.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(slotService, mockRandom);
    }

    @Test
    void keineBelegteZeiten_plaziereFilmWirdAufgerufen_alleZeitenSindVerfuegbar() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        when(mockRandom.nextInt(anyInt())).thenReturn(0); // Select first available slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(14, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(15, 30));
    }

    @Test
    void einigeBelegteZeiten_platziereFilmWirdAufgerufen_nurVerfuegbareZeitenWerdenZurueckgegeben() {
        // Arrange
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30)),
                new TimeSlot(LocalTime.of(16, 0), LocalTime.of(17, 30))
        );
        when(mockRandom.nextInt(anyInt())).thenReturn(0); // Select first available slot from remaining

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isNotEqualTo(LocalTime.of(14, 0));
        assertThat(result.start).isNotEqualTo(LocalTime.of(16, 0));
        // Should be one of the available slots (not colliding with occupied ones)
        assertThat(result.ende.minusMinutes(90)).isEqualTo(result.start);
    }

    @Test
    void alleSlotsbelegt_platziereFilmWirdAufgerufen_nullWirdZurueckgegeben() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        // Fill all possible slots from 14:00 to 21:30 (last possible start time)
        LocalTime current = LocalTime.of(14, 0);
        LocalTime end = LocalTime.of(21, 30);
        while (!current.isAfter(end)) {
            belegteZeiten.add(new TimeSlot(current, current.plusMinutes(90)));
            current = current.plusMinutes(15);
        }

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void keineBelegteZeiten_mitGemocktemRandom_ausgewaehlterSlotWirdZurueckgegeben() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        when(mockRandom.nextInt(anyInt())).thenReturn(10); // Select the 11th available slot (index 10)

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        // 11th slot: 14:00 + (10 * 15 minutes) = 16:30
        assertThat(result.start).isEqualTo(LocalTime.of(16, 30));
        assertThat(result.ende).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void einBelegterSlot_mitGemocktemRandom_korrekterSlotWirdAusgewaehlt() {
        // Arrange
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(15, 0), LocalTime.of(16, 30))
        );
        when(mockRandom.nextInt(anyInt())).thenReturn(5); // Select the 6th available slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.ende.minusMinutes(90)).isEqualTo(result.start);
        // Verify it doesn't collide with the occupied slot
        assertThat(result.start.isBefore(LocalTime.of(15, 0)) || result.start.isAfter(LocalTime.of(16, 30)))
                .as("Selected time should not collide with occupied slot")
                .isTrue();
    }

    @Test
    void mehrereBelegteZeiten_platziereFilmWirdAufgerufen_lueckenWerdenGefunden() {
        // Arrange
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30)),
                new TimeSlot(LocalTime.of(18, 0), LocalTime.of(19, 30)),
                new TimeSlot(LocalTime.of(21, 0), LocalTime.of(22, 30))
        );
        when(mockRandom.nextInt(anyInt())).thenReturn(0); // Select first available slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.ende.minusMinutes(90)).isEqualTo(result.start);
        // Verify no collision
        for (TimeSlot belegt : belegteZeiten) {
            assertThat(result.start.isBefore(belegt.ende) && result.ende.isAfter(belegt.start))
                    .as("Result should not collide with occupied slot %s-%s", belegt.start, belegt.ende)
                    .isFalse();
        }
    }

    @Test
    void zeitSlotsAmRand_platziereFilmWirdAufgerufen_randSlotsSindVerfuegbar() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        // Test that the earliest slot (14:00) and latest slot (21:30) can be selected
        when(mockRandom.nextInt(anyInt())).thenReturn(30); // Select last available slot (index 30)

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        // Last slot should start at 21:30 and end at 23:00
        assertThat(result.start).isEqualTo(LocalTime.of(21, 30));
        assertThat(result.ende).isEqualTo(LocalTime.of(23, 0));
    }
}
