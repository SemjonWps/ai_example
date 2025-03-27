package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class Platz {
    private Long id;
    private int platznummer;
    private int reihe;
    private boolean belegt;
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

    public int getReihe() {
        return reihe;
    }

    public String getReservierungsnummer() {
        return reservierungsnummer;
    }

    public int getPlatznummer() {
        return platznummer;
    }
}
