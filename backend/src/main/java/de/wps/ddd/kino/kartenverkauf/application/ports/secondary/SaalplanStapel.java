package de.wps.ddd.kino.kartenverkauf.application.ports.secondary;

import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface SaalplanStapel {

    Saalplan holeSaalplan(VorstellungId vorstellungId);

    void legeZurueck(Saalplan saalplan);
}
