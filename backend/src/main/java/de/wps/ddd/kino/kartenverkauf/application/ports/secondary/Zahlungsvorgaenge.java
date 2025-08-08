package de.wps.ddd.kino.kartenverkauf.application.ports.secondary;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Zahlungsvorgang;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface Zahlungsvorgaenge {

    void speichere(Zahlungsvorgang zahlungsvorgang);

    Zahlungsvorgang hole(Auftragsnummer auftragsnummer);
}
