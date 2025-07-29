package de.wps.ddd.kino.kartenverkauf.api.model;

public record KinokarteDto(
        String film,
        String beginn,
        String saal,
        int reihe,
        int platz
) {
}
