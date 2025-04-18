package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

public record PlatzNummer(int nummer) implements Comparable<PlatzNummer> {
    @Override
    public int compareTo(PlatzNummer o) {
        return Integer.compare(this.nummer, o.nummer);
    }
}
