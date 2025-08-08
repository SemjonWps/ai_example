package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public enum Zahlungsstatus {
    Ausstehend,
    Eingegangen,
    Abgebrochen,
}
