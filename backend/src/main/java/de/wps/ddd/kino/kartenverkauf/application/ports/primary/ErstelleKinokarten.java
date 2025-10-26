package de.wps.ddd.kino.kartenverkauf.application.ports.primary;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Kinokarte;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ZusammenhaengendePlaetze;

import java.util.List;

public interface ErstelleKinokarten {
    List<Kinokarte> fuer(Auftragsnummer auftragsnummer, VorstellungId vorstellungId, ZusammenhaengendePlaetze gewaehltePlaetze);
}
