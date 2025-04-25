package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

public record Zahlungsbestaetigung(
        Zahlungsanforderung zahlungsanforderung,
        Status status) {
    public enum Status {
        BEZAHLT, ABGELEHNT
    }
}

