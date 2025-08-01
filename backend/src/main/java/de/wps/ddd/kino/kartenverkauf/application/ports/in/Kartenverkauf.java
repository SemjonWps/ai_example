package de.wps.ddd.kino.kartenverkauf.application.ports.in;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsanforderung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;

import java.util.List;

public interface Kartenverkauf {

    Vorstellung holeVorstellung(VorstellungId vorstellungId);

    Saalplan holeSaalplan(VorstellungId vorstellungId);

    ZusammenhaengendePlaetze sucheZusammenhaengendePlaetze(VorstellungId vorstellungId, int platzanzahl);

    Zahlungsanforderung fordereBezahlungAn(VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze);

    List<Kinokarte> erstelleKinokarten(VorstellungId vorstellungId, ZusammenhaengendePlaetze gewaehltePlaetze);

}
