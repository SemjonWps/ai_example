package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Platz {
    
    private PlatzId platzId;
    private boolean istVerkauft;
    private Reservierungsnummer reservierungsnummer;

    public void markiereAlsVerkauft() {
        istVerkauft = true;
    }

    public boolean istFrei() {
        return !istVerkauft && reservierungsnummer == null;
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
