package de.wps.ddd.kino.kartenverkauf.application.ports.secondary;

import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface SaalplanStapel {

    Saalplan holeSaalplan(VorstellungId vorstellungId);

    void legeZurueck(Saalplan saalplan);
}
