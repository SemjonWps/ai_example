package de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record PlatzId(ReiheNummer reihe, PlatzNummer platz) {
}
