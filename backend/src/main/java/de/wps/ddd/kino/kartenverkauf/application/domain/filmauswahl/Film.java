package de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record Film(String name) {
}
