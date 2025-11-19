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

@ExtendWith(MockitoExtension.class)
class SlotServiceTest {

    @Mock
    private Random random;

    @InjectMocks
    private SlotService slotService;

    @BeforeEach
    void setUp() throws Exception {
        // Inject the mocked Random into SlotService using reflection
        Field randomField = SlotService.class.getDeclaredField("random");
        randomField.setAccessible(true);
        randomField.set(slotService, random);
    }

    @Test
    void keineVorstellungenBelegt_alleZeitenVerfuegbar_ersteZeitGewaehlt() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        when(random.nextInt(anyInt())).thenReturn(0); // Select first available slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(14, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(15, 30));
    }

    @Test
    void keineVorstellungenBelegt_alleZeitenVerfuegbar_mittlereZeitGewaehlt() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        when(random.nextInt(anyInt())).thenReturn(12); // Select a middle slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(17, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(18, 30));
    }

    @Test
    void keineVorstellungenBelegt_alleZeitenVerfuegbar_letzteZeitGewaehlt() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        // There are 31 available slots from 14:00 to 21:30 (every 15 minutes)
        // Latest start is 21:30 (ends at 23:00)
        when(random.nextInt(anyInt())).thenReturn(30); // Select last available slot (index 30)

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(21, 30));
        assertThat(result.ende).isEqualTo(LocalTime.of(23, 0));
    }

    @Test
    void eineVorstellungAmAnfang_verfuegbareZeitenNachBelegterZeit() {
        // Arrange
        TimeSlot belegt = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt);
        when(random.nextInt(anyInt())).thenReturn(0); // Select first available slot after occupied

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(15, 30));
        assertThat(result.ende).isEqualTo(LocalTime.of(17, 0));
    }

    @Test
    void eineVorstellungAmEnde_verfuegbareZeitenVorBelegterZeit() {
        // Arrange
        TimeSlot belegt = new TimeSlot(LocalTime.of(21, 0), LocalTime.of(22, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt);
        when(random.nextInt(anyInt())).thenReturn(0); // Select first available slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(14, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(15, 30));
    }

    @Test
    void eineVorstellungInDerMitte_verfuegbareZeitenVorUndNachBelegterZeit() {
        // Arrange
        TimeSlot belegt = new TimeSlot(LocalTime.of(17, 0), LocalTime.of(18, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt);
        when(random.nextInt(anyInt())).thenReturn(0); // Select first available slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(14, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(15, 30));
    }

    @Test
    void mehrereBelegteZeiten_verfuegbareZeitenZwischenBelegungen() {
        // Arrange
        TimeSlot belegt1 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30));
        TimeSlot belegt2 = new TimeSlot(LocalTime.of(18, 0), LocalTime.of(19, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt1, belegt2);
        when(random.nextInt(anyInt())).thenReturn(0); // Select first available slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(15, 30));
        assertThat(result.ende).isEqualTo(LocalTime.of(17, 0));
    }

    @Test
    void mehrereBelegteZeiten_verfuegbareZeitenZwischenBelegungen_letzteZeitImLueckeGewaehlt() {
        // Arrange
        TimeSlot belegt1 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(15, 30));
        TimeSlot belegt2 = new TimeSlot(LocalTime.of(18, 0), LocalTime.of(19, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt1, belegt2);
        // Between 15:30 and 18:00 there are slots at: 15:30, 15:45, 16:00, 16:15, 16:30
        // (16:45 would end at 18:15 which collides with belegt2)
        when(random.nextInt(anyInt())).thenReturn(4); // Select 5th available slot

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(16, 30));
        assertThat(result.ende).isEqualTo(LocalTime.of(18, 0));
    }

    @Test
    void fastVollerTag_nurEineZeitVerfuegbar() {
        // Arrange
        // Create a schedule where only 20:15 is available
        // belegt1: 14:00-17:15, belegt2: 17:30-20:15, belegt3: 21:45-23:00
        // Only slot at 20:15 (ends at 21:45) should be available
        TimeSlot belegt1 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(17, 15));
        TimeSlot belegt2 = new TimeSlot(LocalTime.of(17, 30), LocalTime.of(20, 15));
        TimeSlot belegt3 = new TimeSlot(LocalTime.of(21, 45), LocalTime.of(23, 0));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt1, belegt2, belegt3);
        when(random.nextInt(1)).thenReturn(0); // Only one slot available

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(20, 15));
        assertThat(result.ende).isEqualTo(LocalTime.of(21, 45));
    }

    @Test
    void vollerTag_keineZeitVerfuegbar() {
        // Arrange
        TimeSlot belegt1 = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(17, 0));
        TimeSlot belegt2 = new TimeSlot(LocalTime.of(17, 0), LocalTime.of(20, 0));
        TimeSlot belegt3 = new TimeSlot(LocalTime.of(20, 0), LocalTime.of(23, 0));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt1, belegt2, belegt3);

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void ueberlappendeBelegteZeiten_keineZeitVerfuegbar() {
        // Arrange
        TimeSlot belegt = new TimeSlot(LocalTime.of(13, 0), LocalTime.of(23, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt);

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void belegteZeitKnappeVorStartZeit_alleZeitenVerfuegbar() {
        // Arrange
        TimeSlot belegt = new TimeSlot(LocalTime.of(12, 0), LocalTime.of(13, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt);
        when(random.nextInt(anyInt())).thenReturn(0);

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(14, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(15, 30));
    }

    @Test
    void belegteZeitKnappeNachEndZeit_alleZeitenVerfuegbar() {
        // Arrange
        TimeSlot belegt = new TimeSlot(LocalTime.of(23, 0), LocalTime.of(23, 30));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt);
        when(random.nextInt(anyInt())).thenReturn(0);

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(14, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(15, 30));
    }

    @Test
    void belegteZeitEndetExaktWennNeueBeginnt_keineKollision() {
        // Arrange
        TimeSlot belegt = new TimeSlot(LocalTime.of(14, 0), LocalTime.of(16, 0));
        List<TimeSlot> belegteZeiten = Arrays.asList(belegt);
        when(random.nextInt(anyInt())).thenReturn(0);

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(16, 0));
        assertThat(result.ende).isEqualTo(LocalTime.of(17, 30));
    }

    @Test
    void leereListeBelegterZeiten_alleZeitenVerfuegbar() {
        // Arrange
        List<TimeSlot> belegteZeiten = new ArrayList<>();
        when(random.nextInt(anyInt())).thenReturn(5);

        // Act
        TimeSlot result = slotService.platziereFilm(belegteZeiten);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.start).isEqualTo(LocalTime.of(15, 15));
        assertThat(result.ende).isEqualTo(LocalTime.of(16, 45));
    }
}
