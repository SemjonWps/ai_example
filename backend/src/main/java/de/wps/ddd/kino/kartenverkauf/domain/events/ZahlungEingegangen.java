package de.wps.ddd.kino.kartenverkauf.domain.events;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Auftragsnummer;

public record ZahlungEingegangen(Auftragsnummer auftragsnummer) {
}
