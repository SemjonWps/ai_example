package de.wps.dddschulung.kartenverkauf.domain.domainobjects;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
public class Platz {
    private Long id;
    @Getter
    private Sitz sitz;
    @Getter
    private Reihe reihe;
    private boolean isVerkauft;
    @Getter
    private Reservierungsnummer reservierungsnummer;

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

    public void markiereAlsReserviert(Reservierungsnummer reservierungsnummer) {
        this.reservierungsnummer = reservierungsnummer;
    }

    public void gebeReservierungFrei() {
        reservierungsnummer = null;
    }
}
