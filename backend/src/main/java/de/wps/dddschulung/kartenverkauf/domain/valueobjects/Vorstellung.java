package de.wps.dddschulung.kartenverkauf.domain.valueobjects;

import java.util.UUID;

public record Vorstellung(UUID uuid, Saal saal, Beginn anfangszeit, Filmname filmname) {
}
