package de.wps.ddd.kino.kartenverkauf.api.model;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Sitz;

public record PlatzDto(Reihe reihe, Sitz sitz) {
}
