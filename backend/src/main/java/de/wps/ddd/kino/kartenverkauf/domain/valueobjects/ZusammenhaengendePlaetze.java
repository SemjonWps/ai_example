package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.List;

@ValueObject
public record ZusammenhaengendePlaetze(List<PlatzId> plaetze) {
    public int anzahl() {
        return plaetze.size();
    }
}
