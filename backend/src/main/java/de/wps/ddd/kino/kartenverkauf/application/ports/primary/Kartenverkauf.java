package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.common.architecture.ApplicationService;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Platzanzahl;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ZusammenhaengendePlaetze;

import java.util.List;

@ApplicationService
public interface Kartenverkauf {

    Vorstellung holeVorstellung(VorstellungId vorstellungId);

    Saalplan holeSaalplan(VorstellungId vorstellungId);

    ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(VorstellungId vorstellungId, Platzanzahl platzanzahl);

    Geldbetrag berechneGesamtpreis(VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze);

    List<Kinokarte> erstelleKinokarten(Auftragsnummer auftragsnummer, VorstellungId vorstellungId, ZusammenhaengendePlaetze gewaehltePlaetze);

}
