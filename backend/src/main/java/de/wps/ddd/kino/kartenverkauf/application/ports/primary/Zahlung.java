package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.common.architecture.ApplicationService;
import de.wps.ddd.kino.kartenverkauf.application.domain.events.ZahlungAbgebrochen;
import de.wps.ddd.kino.kartenverkauf.application.domain.events.ZahlungEingegangen;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ZusammenhaengendePlaetze;

@ApplicationService
public interface Zahlung {

    Auftragsnummer starteZahlungsvorgang(Geldbetrag gesamtpreis, VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze);

    Zahlungsstatus status(Auftragsnummer auftragsnummer);

    void verarbeite(ZahlungEingegangen zahlungErfolgt);

    void verarbeite(ZahlungAbgebrochen zahlungAbgebrochen);
}
