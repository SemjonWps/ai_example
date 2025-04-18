package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;


public record ReiheNummer(int nummer) implements Comparable<ReiheNummer> {
    @Override
    public int compareTo(ReiheNummer o) {
        return Integer.compare(this.nummer, o.nummer);
    }
}
