package de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl;

import org.jmolecules.ddd.annotation.ValueObject;

import java.time.LocalDateTime;

@ValueObject
public record Beginn(LocalDateTime zeitpunkt) {
}
