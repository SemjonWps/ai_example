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
    private boolean isVerkauft;
    @Getter
    private String reservierungsnummer;

    public void markiereAlsVerkauft() {
        isVerkauft = true;
    }

    public boolean istFrei() {
        return !isVerkauft && reservierungsnummer == null;
    }

    public boolean istBelegt() {
        return isVerkauft || reservierungsnummer != null;
    }

    public boolean istVerkauft() {
        return isVerkauft;
    }

    public void markiereAlsReserviert(String reservierungsnummer) {
        this.reservierungsnummer = reservierungsnummer;
    }

    public void gebeReservierungFrei() {
        reservierungsnummer = null;
    }
}
