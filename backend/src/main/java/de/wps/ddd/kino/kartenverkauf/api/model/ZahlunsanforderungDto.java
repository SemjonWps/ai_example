package de.wps.ddd.kino.kartenverkauf.api.model;

public record ZahlunsanforderungDto(
        VorstellungDto vorstellung,
        ZusammenhaengendePlaetzeDto plaetze,
        GeldbetragDto betrag
) {
}
