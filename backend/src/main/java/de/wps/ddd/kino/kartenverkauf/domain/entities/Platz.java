package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Platz {

    private PlatzId id;
    private boolean istVerkauft;
    private Reservierungsnummer reservierung;

    public void markiereAlsVerkauft() {
        istVerkauft = true;
    }

    public boolean istFrei() {
        return !istVerkauft && reservierung == null;
    }

    public boolean istVerkauft() {
        return istVerkauft;
    }

    public void markiereAlsReserviert(Reservierungsnummer reservierungsnummer) {
        this.reservierung = reservierungsnummer;
    }

    public void gebeReservierungFrei() {
        reservierung = null;
    }
}
