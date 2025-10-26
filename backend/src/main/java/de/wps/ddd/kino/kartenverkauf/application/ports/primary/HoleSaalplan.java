package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;

public interface HoleSaalplan {
    Saalplan fuer(VorstellungId vorstellungId);
}
