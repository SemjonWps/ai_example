package de.wps.ddd.kino.filmauswahl.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.*;

@Component
public class SlotService {

    private static final LocalTime START_TIME = LocalTime.of(14, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    private static final int FILM_DURATION_MINUTES = 90;
    private static final int TIME_SLOT_STEP_MINUTES = 15;

    private final Random random = new Random();

    public TimeSlot platziereFilm(List<TimeSlot> belegteZeiten) {
        List<LocalTime> moeglicheStarts = berechneVerfuegbareZeiten(belegteZeiten);

        if (moeglicheStarts.isEmpty()) {
            return null;
        }

        LocalTime startZeit = moeglicheStarts.get(random.nextInt(moeglicheStarts.size()));
        LocalTime endeZeit = startZeit.plusMinutes(FILM_DURATION_MINUTES);

        return new TimeSlot(startZeit, endeZeit);
    }

    private List<LocalTime> berechneVerfuegbareZeiten(List<TimeSlot> belegteZeiten) {
        List<LocalTime> verfuegbar = new ArrayList<>();
        LocalTime spaetesterStart = END_TIME.minusMinutes(FILM_DURATION_MINUTES);

        LocalTime current = START_TIME;
        while (!current.isAfter(spaetesterStart)) {
            LocalTime endeZeit = current.plusMinutes(FILM_DURATION_MINUTES);

            if (!hatKollision(belegteZeiten, current, endeZeit)) {
                verfuegbar.add(current);
            }

            current = current.plusMinutes(TIME_SLOT_STEP_MINUTES);
        }

        return verfuegbar;
    }

    private boolean hatKollision(List<TimeSlot> belegteZeiten, LocalTime start, LocalTime ende) {
        for (TimeSlot belegt : belegteZeiten) {
            if (start.isBefore(belegt.ende) && ende.isAfter(belegt.start)) {
                return true;
            }
        }
        return false;
    }
}
