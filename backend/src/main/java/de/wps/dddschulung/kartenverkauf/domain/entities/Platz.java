package de.wps.dddschulung.kartenverkauf.domain.entities;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
public class Platz {
    private Long id;
    private Sitz sitz;
    private Reihe reihe;
    @Getter
    @Accessors(fluent = true)
    private boolean istVerkauft;
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

    public void markiereAlsReserviert(Reservierungsnummer reservierungsnummer) {
        this.reservierungsnummer = reservierungsnummer;
    }

    public void gebeReservierungFrei() {
        reservierungsnummer = null;
    }
}
