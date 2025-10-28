package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;

public interface BerechneGesamtpreis {
    Geldbetrag fuer(VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze);
}
