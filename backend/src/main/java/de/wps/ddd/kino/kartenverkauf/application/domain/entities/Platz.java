package de.wps.ddd.kino.kartenverkauf.application.domain.entities;

import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Reservierungsnummer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.Entity;
import org.jmolecules.ddd.annotation.Identity;

@Entity
@Getter
@AllArgsConstructor
public class Platz {

    @Identity
    private final PlatzId id;
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
