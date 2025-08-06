package de.wps.ddd.kino.kartenverkauf.adapters.web.model;

public record ZahlungsvorgangDto(
        String auftragsnummer,
        String vorstellungId,
        ZusammenhaengendePlaetzeDto plaetze,
        GeldbetragDto betrag
) {
}
