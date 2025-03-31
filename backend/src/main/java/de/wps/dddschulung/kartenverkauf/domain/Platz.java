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
    private boolean verkauft;
    @Getter
    private String reservierungsnummer;

    public void markiereAlsVerkauft() {
        verkauft = true;
    }

    public boolean istFrei() {
        return !verkauft && reservierungsnummer == null;
    }

    public boolean istBelegt() {
        return verkauft || reservierungsnummer != null;
    }

    public void markiereAlsReserviert(String reservierungsnummer) {
        this.reservierungsnummer = reservierungsnummer;
    }
}
