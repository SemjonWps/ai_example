package de.wps.ddd.kino.kartenverkauf.adapters.web.model;

import java.util.UUID;

public record PreisanfrageDto(UUID vorstellungId, ZusammenhaengendePlaetzeDto plaetze) {
}
