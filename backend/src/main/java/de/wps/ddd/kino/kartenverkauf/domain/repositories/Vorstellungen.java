package de.wps.ddd.kino.kartenverkauf.domain.repositories;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;

public interface Vorstellungen {
    Vorstellung holeVorstellung(VorstellungId vorstellungId);
}
