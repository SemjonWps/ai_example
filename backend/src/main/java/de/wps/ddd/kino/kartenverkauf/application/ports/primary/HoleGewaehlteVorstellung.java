package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.VorstellungId;

public interface HoleGewaehlteVorstellung {
    Vorstellung fuer(VorstellungId vorstellungId);
}
