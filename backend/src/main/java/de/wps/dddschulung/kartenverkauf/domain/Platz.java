package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class Platz {
    private Long id;
    @Getter
    private int platznummer;
    @Getter
    private int reihe;
    private boolean belegt;
    @Getter
    private String reservierungsnummer;

    public void markiereAlsVerkauft() {
        belegt = true;
    }

    public boolean istFrei() {
        return !belegt;
    }

    public boolean istBelegt() {
        return belegt;
    }
}
