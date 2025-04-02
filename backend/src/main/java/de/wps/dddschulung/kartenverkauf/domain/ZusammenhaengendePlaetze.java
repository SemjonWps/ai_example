package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;

import java.util.List;

public record ZusammenhaengendePlaetze(List<Platz> plaetze) {
}
