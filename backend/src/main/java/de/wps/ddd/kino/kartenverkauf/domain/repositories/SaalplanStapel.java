package de.wps.ddd.kino.kartenverkauf.domain.repositories;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;

import java.util.UUID;

public interface SaalplanStapel {

    Saalplan holeSaalplan(UUID vorstellungUUID);

    void legeZurueck(Saalplan saalplan);
}
