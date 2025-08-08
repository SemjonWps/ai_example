package de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects;

import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
public record ReiheNummer(int nummer) implements Comparable<ReiheNummer> {
    @Override
    public int compareTo(ReiheNummer o) {
        return Integer.compare(this.nummer, o.nummer);
    }
}
