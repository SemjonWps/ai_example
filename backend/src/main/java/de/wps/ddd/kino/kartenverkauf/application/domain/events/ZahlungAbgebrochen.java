package de.wps.ddd.kino.kartenverkauf.application.domain.events;

import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record ZahlungAbgebrochen(Auftragsnummer auftragsnummer) {
}
