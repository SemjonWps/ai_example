package de.wps.dddschulung.kartenverkauf.domain.entities;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class Platz {
    @Getter
    private Long id;
    @Getter
    private Sitz sitz;
    @Getter
    private Reihe reihe;
    private boolean istVerkauft;
    @Getter
    private Reservierungsnummer reservierungsnummer;

    public void markiereAlsVerkauft() {
        istVerkauft = true;
    }

    public boolean istFrei() {
        return !istVerkauft && reservierungsnummer == null;
    }

    public boolean istBelegt() {
        return istVerkauft || reservierungsnummer != null;
    }

    public boolean istVerkauft() {
        return istVerkauft;
    }

    public void markiereAlsReserviert(Reservierungsnummer reservierungsnummer) {
        this.reservierungsnummer = reservierungsnummer;
    }

    public void gebeReservierungFrei() {
        reservierungsnummer = null;
    }
}
