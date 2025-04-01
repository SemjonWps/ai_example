package de.wps.dddschulung.kartenverkauf.persistence;

import de.wps.dddschulung.kartenverkauf.domain.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class SaalplanStapelImpl implements SaalplanStapel {
    private final SaalplanRepository saalplanRepository;

    public Saalplan holeSaalplan(Vorstellung vorstellung) {
        return null;
    }

    public void legeZurueck(Saalplan saalplan) {

    }
}
