package de.wps.ddd.kino.kartenverkauf.application.ports.secondary;

import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Saal;

import java.util.Optional;

public interface SaalKonfiguration {
    record SaalAbmessungen(int reihen, int spalten) {
    }

    Optional<SaalAbmessungen> findeAbmessungen(Saal saal);
}