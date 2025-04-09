package de.wps.dddschulung.kartenverkauf.api.model;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;

public record PlatzDto(Reihe reihe, Sitz sitz) {
}
