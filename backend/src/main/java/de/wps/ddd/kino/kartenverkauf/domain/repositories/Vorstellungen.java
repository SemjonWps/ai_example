package de.wps.ddd.kino.kartenverkauf.domain.repositories;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;

import java.util.UUID;

public interface Vorstellungen {
    Vorstellung holeVorstellung(UUID vorstellungId);
}
