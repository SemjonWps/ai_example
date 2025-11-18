package de.wps.ddd.kino.filmauswahl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for SlotService without using mocks.
 * Tests the berechneVerfuegbareZeiten method indirectly through platziereFilm.
 */
class SlotServiceTest {

    private SlotService slotService;

    @BeforeEach
    void setUp() {
        slotService = new SlotService();
    }

    @Test
    void platziereFilm_mitLeerenBelegtenZeiten_sollteZeitSlotZurueckgeben() {
        // Given: No occupied time slots
        List<TimeSlot> belegteZeiten = new ArrayList<>();

        // When: Place a film
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Then: Should return a valid time slot
        assertThat(result).isNotNull();
        assertThat(result.start).isNotNull();
        assertThat(result.ende).isNotNull();
        
        // Start time should be between 14:00 and 21:30 (latest start for 90-min film ending by 23:00)
        assertThat(result.start).isBetween(LocalTime.of(14, 0), LocalTime.of(21, 30));
        
        // End time should be 90 minutes after start
        assertThat(result.ende).isEqualTo(result.start.plusMinutes(90));
        
        // End time should not exceed 23:00
        assertThat(result.ende).isBeforeOrEqualTo(LocalTime.of(23, 0));
    }

    @Test
    void platziereFilm_mitEinemBelegtenSlot_sollteAnderenSlotFinden() {
        // Given: One occupied time slot from 14:00 to 15:30
        TimeSlot belegterSlot = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegterSlot);

        // When: Place a film multiple times to get different results
        List<LocalTime> gefundeneStarts = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            TimeSlot result = slotService.platziereFilm(belegteZeiten);
            if (result != null && !gefundeneStarts.contains(result.start)) {
                gefundeneStarts.add(result.start);
            }
        }

        // Then: Should find slots that don't collide with 14:00-15:30
        // Any start at or after 15:30 should be valid, or any that end at or before 14:00
        for (LocalTime start : gefundeneStarts) {
            LocalTime ende = start.plusMinutes(90);
            
            // Check no collision: either ends before or starts after the occupied slot
            boolean noCollision = ende.isBefore(belegterSlot.start) || 
                                 ende.equals(belegterSlot.start) || 
                                 start.isAfter(belegterSlot.ende) || 
                                 start.equals(belegterSlot.ende);
            
            assertThat(noCollision)
                .withFailMessage("Start %s (ende %s) should not collide with occupied slot %s-%s", 
                                start, ende, belegterSlot.start, belegterSlot.ende)
                .isTrue();
        }
        
        // Should have found multiple valid slots (there are many available)
        assertThat(gefundeneStarts).hasSizeGreaterThan(1);
    }

    @Test
    void platziereFilm_mitMehrerenNichtUeberlappendenSlots_sollteFreienSlotFinden() {
        // Given: Multiple non-overlapping occupied slots
        List<TimeSlot> belegteZeiten = Arrays.asList(
            new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30)),  // 14:00-15:30
            new TimeSlot(LocalTime.of(16, 0), LocalTime.of(17, 30)),  // 16:00-17:30
            new TimeSlot(LocalTime.of(18, 0), LocalTime.of(19, 30))   // 18:00-19:30
        );

        // When: Place a film multiple times
        List<LocalTime> gefundeneStarts = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            TimeSlot result = slotService.platziereFilm(belegteZeiten);
            if (result != null && !gefundeneStarts.contains(result.start)) {
                gefundeneStarts.add(result.start);
            }
        }

        // Then: Should find slots that don't collide with any occupied slot
        for (LocalTime start : gefundeneStarts) {
            LocalTime ende = start.plusMinutes(90);
            
            for (TimeSlot belegt : belegteZeiten) {
                boolean noCollision = ende.isBefore(belegt.start) || 
                                     ende.equals(belegt.start) || 
                                     start.isAfter(belegt.ende) || 
                                     start.equals(belegt.ende);
                
                assertThat(noCollision)
                    .withFailMessage("Start %s (ende %s) should not collide with occupied slot %s-%s", 
                                    start, ende, belegt.start, belegt.ende)
                    .isTrue();
            }
        }
        
        // Should find valid slots (e.g., 15:30-17:00, 17:30-19:00, 19:30-21:00, 20:00+, etc.)
        assertThat(gefundeneStarts).isNotEmpty();
    }

    @Test
    void platziereFilm_mitVollstaendigBelegtemTag_sollteNullZurueckgeben() {
        // Given: Fully booked day - slots every 15 minutes from 14:00 to latest possible start
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        LocalTime current = LocalTime.of(14, 0);
        LocalTime latestStart = LocalTime.of(21, 30); // Latest start for 90-min film ending by 23:00
        
        while (!current.isAfter(latestStart)) {
            belegteZeiten.add(new TimeSlot(current, current.plusMinutes(90)));
            current = current.plusMinutes(15); // Step by 15 minutes
        }

        // When: Try to place a film
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Then: Should return null as no slot is available
        assertThat(result).isNull();
    }

    @Test
    void platziereFilm_mitAngrenzendenSlots_sollteFreienSlotFinden() {
        // Given: Adjacent time slots (one ends exactly when the next could start)
        List<TimeSlot> belegteZeiten = Arrays.asList(
            new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30)),  // 14:00-15:30
            new TimeSlot(LocalTime.of(15, 30), LocalTime.of(17, 0))   // 15:30-17:00 (adjacent)
        );

        // When: Place a film
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Then: Should find a slot that doesn't overlap
        // Valid slots would be before 14:00 or after 17:00
        assertThat(result).isNotNull();
        
        LocalTime start = result.start;
        LocalTime ende = result.ende;
        
        // Check no collision with any occupied slot
        for (TimeSlot belegt : belegteZeiten) {
            boolean noCollision = ende.isBefore(belegt.start) || 
                                 ende.equals(belegt.start) || 
                                 start.isAfter(belegt.ende) || 
                                 start.equals(belegt.ende);
            
            assertThat(noCollision)
                .withFailMessage("Start %s (ende %s) should not collide with occupied slot %s-%s", 
                                start, ende, belegt.start, belegt.ende)
                .isTrue();
        }
    }

    @Test
    void platziereFilm_mitSlotAmAnfang_sollteSlotNachDemBelegtenFinden() {
        // Given: Occupied slot at the very beginning
        List<TimeSlot> belegteZeiten = Arrays.asList(
            new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30))
        );

        // When: Place multiple films
        List<LocalTime> gefundeneStarts = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            TimeSlot result = slotService.platziereFilm(belegteZeiten);
            if (result != null && !gefundeneStarts.contains(result.start)) {
                gefundeneStarts.add(result.start);
            }
        }

        // Then: Should find slots starting at 15:30 or later
        for (LocalTime start : gefundeneStarts) {
            assertThat(start).isAfterOrEqualTo(LocalTime.of(15, 30));
        }
        
        assertThat(gefundeneStarts).isNotEmpty();
    }

    @Test
    void platziereFilm_mitSlotAmEnde_sollteSlotVorDemBelegtenFinden() {
        // Given: Occupied slot at the very end
        List<TimeSlot> belegteZeiten = Arrays.asList(
            new TimeSlot(LocalTime.of(21, 0), LocalTime.of(22, 30))
        );

        // When: Place multiple films
        List<LocalTime> gefundeneStarts = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            TimeSlot result = slotService.platziereFilm(belegteZeiten);
            if (result != null && !gefundeneStarts.contains(result.start)) {
                gefundeneStarts.add(result.start);
            }
        }

        // Then: Should find slots that end at or before 21:00
        for (LocalTime start : gefundeneStarts) {
            LocalTime ende = start.plusMinutes(90);
            assertThat(ende).isBeforeOrEqualTo(LocalTime.of(21, 0));
        }
        
        assertThat(gefundeneStarts).isNotEmpty();
    }

    @Test
    void platziereFilm_mitUeberlappendenSlots_sollteNurFreieSlotsWaehlen() {
        // Given: Overlapping occupied slots
        List<TimeSlot> belegteZeiten = Arrays.asList(
            new TimeSlot(LocalTime.of(15, 0), LocalTime.of(16, 30)),  // 15:00-16:30
            new TimeSlot(LocalTime.of(16, 0), LocalTime.of(17, 30))   // 16:00-17:30 (overlaps)
        );

        // When: Place a film multiple times
        List<LocalTime> gefundeneStarts = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            TimeSlot result = slotService.platziereFilm(belegteZeiten);
            if (result != null && !gefundeneStarts.contains(result.start)) {
                gefundeneStarts.add(result.start);
            }
        }

        // Then: Should find slots that don't collide with either occupied slot
        for (LocalTime start : gefundeneStarts) {
            LocalTime ende = start.plusMinutes(90);
            
            for (TimeSlot belegt : belegteZeiten) {
                boolean noCollision = ende.isBefore(belegt.start) || 
                                     ende.equals(belegt.start) || 
                                     start.isAfter(belegt.ende) || 
                                     start.equals(belegt.ende);
                
                assertThat(noCollision)
                    .withFailMessage("Start %s (ende %s) should not collide with occupied slot %s-%s", 
                                    start, ende, belegt.start, belegt.ende)
                    .isTrue();
            }
        }
        
        assertThat(gefundeneStarts).isNotEmpty();
    }

    @Test
    void platziereFilm_generiertZeitSlotsIn15MinutenSchritten() {
        // Given: No occupied slots
        List<TimeSlot> belegteZeiten = new ArrayList<>();

        // When: Place multiple films to collect various start times
        List<LocalTime> gefundeneStarts = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TimeSlot result = slotService.platziereFilm(belegteZeiten);
            if (result != null && !gefundeneStarts.contains(result.start)) {
                gefundeneStarts.add(result.start);
            }
        }

        // Then: All start times should be at 0, 15, 30, or 45 minutes past the hour
        for (LocalTime start : gefundeneStarts) {
            int minute = start.getMinute();
            assertThat(minute % 15)
                .withFailMessage("Start time %s should be in 15-minute increments", start)
                .isEqualTo(0);
        }
        
        assertThat(gefundeneStarts).isNotEmpty();
    }
}
