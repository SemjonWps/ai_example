package de.wps.dddschulung.kartenverkauf.domain.repositories;

import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;

import java.util.UUID;

public interface SaalplanStapel {

    Saalplan holeSaalplan(UUID vorstellungUUID);

    void legeZurueck(Saalplan saalplan);
}
