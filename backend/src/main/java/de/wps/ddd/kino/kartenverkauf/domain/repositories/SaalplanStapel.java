package de.wps.ddd.kino.kartenverkauf.domain.repositories;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;

public interface SaalplanStapel {

    Saalplan holeSaalplan(VorstellungId vorstellungId);

    void legeZurueck(Saalplan saalplan);
}
