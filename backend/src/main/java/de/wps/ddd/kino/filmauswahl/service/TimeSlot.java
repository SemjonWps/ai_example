package de.wps.ddd.kino.filmauswahl.service;

import java.time.*;

public class TimeSlot {
    public final LocalTime start;
    public final LocalTime ende;

    TimeSlot(LocalTime start, LocalTime ende) {
        this.start = start;
        this.ende = ende;
    }
}
