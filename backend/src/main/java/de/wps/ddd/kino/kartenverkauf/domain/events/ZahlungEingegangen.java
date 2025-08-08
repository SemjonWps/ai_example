package de.wps.ddd.kino.kartenverkauf.domain.events;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Auftragsnummer;
import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record ZahlungEingegangen(Auftragsnummer auftragsnummer) {
}
