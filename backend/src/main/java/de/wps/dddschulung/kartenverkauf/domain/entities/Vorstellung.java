package de.wps.dddschulung.kartenverkauf.domain.entities;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Saal;

import java.util.UUID;

public record Vorstellung(UUID uuid, Saal saal, Beginn anfangszeit, Filmname filmname) {
}
