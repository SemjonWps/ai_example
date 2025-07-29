package de.wps.ddd.kino.kartenverkauf.api.model;

import java.util.UUID;

public record PreisanfrageDto(UUID vorstellungId, ZusammenhaengendePlaetzeDto plaetze) {
}
