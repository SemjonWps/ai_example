package de.wps.ddd.kino.kartenverkauf.application.ports.in;

import de.wps.ddd.kino.kartenverkauf.domain.events.ZahlungAbgebrochen;
import de.wps.ddd.kino.kartenverkauf.domain.events.ZahlungEingegangen;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsstatus;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;

public interface Zahlung {

    Auftragsnummer starteZahlungsvorgang(Geldbetrag gesamtpreis, VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze);

    Zahlungsstatus status(Auftragsnummer auftragsnummer);

    void verarbeite(ZahlungEingegangen zahlungErfolgt);

    void verarbeite(ZahlungAbgebrochen zahlungAbgebrochen);
}
