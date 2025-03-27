package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class Platz {
    private Long id;
    private int platznummer;
    private int reihe;
    private boolean belegt = false;
    private String reservierungsnummer = null;

    public void markiereAlsVerkauft() {
        belegt = true;
    }

    public boolean isBelegt() {
        return belegt;
    }

    public int getReihe() {
        return reihe;
    }
}
