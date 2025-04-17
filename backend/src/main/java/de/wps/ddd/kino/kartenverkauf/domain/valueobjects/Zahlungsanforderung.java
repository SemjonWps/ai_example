package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

import java.util.UUID;

public record Zahlungsanforderung(UUID vorstellungUuid, ZusammenhaengendePlaetze zusammenhaengendePlaetze,
                                  Geldbetrag preis) {
}
