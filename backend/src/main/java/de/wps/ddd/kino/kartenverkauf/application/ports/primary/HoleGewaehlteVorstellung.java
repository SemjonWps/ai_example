package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.VorstellungId;

public interface HoleGewaehlteVorstellung {
    Vorstellung fuer(VorstellungId vorstellungId);
}
