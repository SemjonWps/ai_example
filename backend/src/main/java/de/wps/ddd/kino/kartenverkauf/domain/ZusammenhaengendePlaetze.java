package de.wps.ddd.kino.kartenverkauf.domain;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;

import java.util.List;

public record ZusammenhaengendePlaetze(List<Platz> plaetze) {
    public int anzahl() {
        return plaetze.size();
    }
}
