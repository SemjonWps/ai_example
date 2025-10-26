package de.wps.ddd.kino.kartenverkauf.application.domain.programm;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record VorstellungId(UUID uuid) {
}
