package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;

public record Zahlungsanforderung(
        Vorstellung vorstellung,
        ZusammenhaengendePlaetze plaetze,
        Geldbetrag betrag) {
}
