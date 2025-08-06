package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

import java.util.UUID;

public record Auftragsnummer(UUID nummer) {
    public static Auftragsnummer neueAuftragsnummer() {
        return new Auftragsnummer(UUID.randomUUID());
    }
}
