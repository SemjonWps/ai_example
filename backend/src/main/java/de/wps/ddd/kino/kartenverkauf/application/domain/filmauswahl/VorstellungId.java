package de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record VorstellungId(UUID uuid) {
}
