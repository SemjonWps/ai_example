package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;

public interface HoleSaalplan {
    Saalplan fuer(VorstellungId vorstellungId);
}
