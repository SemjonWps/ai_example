package de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record VorstellungId(UUID uuid) {
}
