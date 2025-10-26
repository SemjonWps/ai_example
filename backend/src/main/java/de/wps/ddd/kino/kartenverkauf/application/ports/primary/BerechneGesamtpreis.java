package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ZusammenhaengendePlaetze;

public interface BerechneGesamtpreis {
    Geldbetrag fuer(VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze);
}
