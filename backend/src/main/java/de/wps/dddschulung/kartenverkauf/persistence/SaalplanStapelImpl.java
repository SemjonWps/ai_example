package de.wps.dddschulung.kartenverkauf.persistence;

import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class SaalplanStapelImpl implements SaalplanStapel {
    private final SaalplanRepository saalplanRepository;

    public Saalplan holeSaalplan(Vorstellung vorstellung) {
        SaalplanEntity saalplanEntity = saalplanRepository.findBySaalAndAnfangszeit(vorstellung.saal().name(), vorstellung.anfangszeit().zeitpunkt());
        return null;
    }

    public void legeZurueck(Saalplan saalplan) {

    }
}
