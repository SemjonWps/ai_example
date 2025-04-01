package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;

public interface SaalplanStapel {

    Saalplan holeSaalplan(Vorstellung vorstellung);

    void legeZurueck(Saalplan saalplan);
}
