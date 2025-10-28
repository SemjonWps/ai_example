package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platzanzahl;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;

public interface SucheZusammenhaengendePlaetze {
    ZusammenhaengendePlaetze fuer(VorstellungId vorstellungId, Platzanzahl platzanzahl);
}
