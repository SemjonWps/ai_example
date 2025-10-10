package de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects;

import org.jmolecules.ddd.annotation.ValueObject;
import org.springframework.util.Assert;

@ValueObject
public record Platzanzahl(int value) {
    public Platzanzahl {
        Assert.isTrue(value > 0, "Mindestens ein Platz muss angefragt werden.");
    }
}
