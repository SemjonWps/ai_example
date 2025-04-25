package de.wps.ddd.kino.kartenverkauf.api.model;

public record KinokarteDto(
        VorstellungDto vorstellung,
        PlatzIdDto platz,
        GeldbetragDto preis
) {
}
