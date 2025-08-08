package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

import org.jmolecules.ddd.annotation.ValueObject;

import java.util.UUID;

@ValueObject
public record Auftragsnummer(UUID nummer) {
    public static Auftragsnummer neueAuftragsnummer() {
        return new Auftragsnummer(UUID.randomUUID());
    }
}
