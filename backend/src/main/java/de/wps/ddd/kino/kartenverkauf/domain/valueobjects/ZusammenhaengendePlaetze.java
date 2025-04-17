package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

import java.util.List;

public record ZusammenhaengendePlaetze(List<PlatzId> plaetze) {
    public int anzahl() {
        return plaetze.size();
    }
}
