package de.wps.ddd.kino.kartenverkauf.adapters.web.model;

public record ZahlunsanforderungDto(
        VorstellungDto vorstellung,
        ZusammenhaengendePlaetzeDto plaetze,
        GeldbetragDto betrag
) {
}
