package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;

public interface HoleGewaehlteVorstellung {
    Vorstellung fuer(VorstellungId vorstellungId);
}
