package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Platz {
    private Long id;
    private Platznummer platznummer;
    private Reihennummer reihennummer;
    private boolean istVerkauft;
    private Reservierungsnummer reservierungsnummer;
    private Long saalplan_id;

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
