package de.wps.ddd.kino.filmauswahl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SlotServiceTest {

    @Mock
    private Random random;

    private SlotService slotService;

    @BeforeEach
    void setUp() {
        slotService = new SlotService(random);
    }

    @Test
    void leereZeitslots_alleZeitenSindVerfuegbar_slotWirdPlatziert() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        when(random.nextInt(anyInt())).thenReturn(0); // Always select first available slot
        
        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(14, 0)); // First slot at 14:00
        assertThat(result.ende).isEqualTo(LocalTime.of(15, 30)); // 90 minutes later
    }

    @Test
    void leereZeitslots_letzterSlotWirdGewaehlt_slotWirdPlatziert() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        // When size is 31 (slots from 14:00 to 21:30 in 15min intervals), return index 30 (last one)
        when(random.nextInt(31)).thenReturn(30);
        
        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(21, 30)); // Last possible slot
        assertThat(result.ende).isEqualTo(LocalTime.of(23, 0));
    }

    @Test
    void einBelegterSlot_andereSlotsVerfuegbar_ersterVerfuegbarerSlotWirdGewaehlt() {
        // Arrange
        // Block slot from 15:00-16:30, so 14:00-15:30 would end at 15:30 which overlaps
        // First non-overlapping slot would be 16:30-18:00
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(15, 0), LocalTime.of(16, 30))
        );
        when(random.nextInt(anyInt())).thenReturn(0); // Select first available slot
        
        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);
        
        // Assert
        assertThat(result).isNotNull();
        // First available slot that doesn't overlap is 16:30
        assertThat(result.start).isEqualTo(LocalTime.of(16, 30));
        assertThat(result.ende).isEqualTo(LocalTime.of(18, 0));
        
        // Verify no collision with existing slot
        boolean hasCollision = result.start.isBefore(belegteZeiten.get(0).ende) && 
                              result.ende.isAfter(belegteZeiten.get(0).start);
        assertThat(hasCollision).isFalse();
    }

    @Test
    void einBelegterSlot_zweiVerfuegbar_zweiterSlotWirdGewaehlt() {
        // Arrange
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(14, 15), LocalTime.of(15, 45))
        );
        when(random.nextInt(anyInt())).thenReturn(1); // Select second available slot
        
        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);
        
        // Assert
        assertThat(result).isNotNull();
        // Second available slot should be at 15:45 or later
        assertThat(result.start).isAfterOrEqualTo(LocalTime.of(15, 45));
        
        // Verify no collision with existing slot
        boolean hasCollision = result.start.isBefore(belegteZeiten.get(0).ende) && 
                              result.ende.isAfter(belegteZeiten.get(0).start);
        assertThat(hasCollision).isFalse();
    }

    @Test
    void mehrfachBelegteSlots_verfuegbareSlotsExistieren_mittelsterSlotWirdGewaehlt() {
        // Arrange
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30)),
                new TimeSlot(LocalTime.of(16, 0), LocalTime.of(17, 30)),
                new TimeSlot(LocalTime.of(20, 0), LocalTime.of(21, 30))
        );
        when(random.nextInt(anyInt())).thenReturn(2); // Select third available slot
        
        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isAfterOrEqualTo(LocalTime.of(14, 0));
        assertThat(result.ende).isBeforeOrEqualTo(LocalTime.of(23, 0));
        assertThat(result.ende).isEqualTo(result.start.plusMinutes(90));
        
        // Verify no collision with any existing slot
        for (TimeSlot belegt : belegteZeiten) {
            boolean hasCollision = result.start.isBefore(belegt.ende) && 
                                  result.ende.isAfter(belegt.start);
            assertThat(hasCollision).isFalse();
        }
    }

    @Test
    void alleSlotsBelegt_keineVerfuegbareZeiten_nullWirdZurueckgegeben() {
        // Arrange
        // Fill all available slots from 14:00 to 21:30 (last possible start time for 90 min film)
        List<TimeSlot> belegteZeiten = new ArrayList<>();
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
    void letzterMoeglicherSlot_nurEinerVerfuegbar_slotWirdPlatziert() {
        // Arrange
        // Block a large continuous slot from 14:00 to 21:15, leaving only 21:15 and 21:30
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(14, 0), LocalTime.of(21, 15))
        );
        
        when(random.nextInt(2)).thenReturn(0); // Two slots available (21:15 and 21:30), select first
        
        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(21, 15)); // First available after blocked slot
        assertThat(result.ende).isEqualTo(LocalTime.of(22, 45));
    }

    @Test
    void grenzwerteStartZeit_fruehesterSlotVerfuegbar_ersterSlotWirdGewaehlt() {
        // Arrange
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(15, 30), LocalTime.of(17, 0))
        );
        when(random.nextInt(anyInt())).thenReturn(0); // Select first available slot
        
        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(14, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(15, 30));
    }

    @Test
    void grenzwerteEndeZeit_spaetesterSlotVerfuegbar_spaeterSlotWirdGewaehlt() {
        // Arrange
        List<TimeSlot> belegteZeiten = Arrays.asList(
                new TimeSlot(LocalTime.of(14, 0), LocalTime.of(20, 0))
        );
        when(random.nextInt(anyInt())).thenReturn(0); // Select first available slot after 20:00
        
        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);
        
        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(20, 0)); // First available after blocked slot
        assertThat(result.ende).isEqualTo(LocalTime.of(21, 30));
    }
}
