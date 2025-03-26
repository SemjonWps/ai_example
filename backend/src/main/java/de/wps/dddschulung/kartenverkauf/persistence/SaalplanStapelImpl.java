package de.wps.dddschulung.kartenverkauf.persistence;

import de.wps.dddschulung.kartenverkauf.domain.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.Vorstellung;
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
