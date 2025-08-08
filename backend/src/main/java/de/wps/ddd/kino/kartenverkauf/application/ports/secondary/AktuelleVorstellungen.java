package de.wps.ddd.kino.kartenverkauf.application.ports.secondary;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface AktuelleVorstellungen {
    Vorstellung holeVorstellung(VorstellungId vorstellungId);
}
