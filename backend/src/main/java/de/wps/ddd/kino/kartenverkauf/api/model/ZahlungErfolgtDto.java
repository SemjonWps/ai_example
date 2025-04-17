package de.wps.ddd.kino.kartenverkauf.api.model;

import java.util.UUID;

public record ZahlungErfolgtDto(PlatzDto[] platzDtos, UUID vorstellungUuid) {
}
