package de.wps.ddd.kino.kartenverkauf.domain.services;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsanforderung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;

import java.util.UUID;

public interface BezahlService {
    Zahlungsanforderung fordereBezahlungAn(UUID vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze, Geldbetrag preis);
}
