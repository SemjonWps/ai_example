package de.wps.dddschulung.kartenverkauf.domain.repositories;

import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;

public interface SaalplanStapel {

    Saalplan holeSaalplan(Vorstellung vorstellung);

    void legeZurueck(Saalplan saalplan);
}
