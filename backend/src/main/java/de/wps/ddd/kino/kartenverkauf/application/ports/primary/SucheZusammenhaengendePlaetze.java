package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Platzanzahl;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ZusammenhaengendePlaetze;

public interface SucheZusammenhaengendePlaetze {
    ZusammenhaengendePlaetze fuer(VorstellungId vorstellungId, Platzanzahl platzanzahl);
}
