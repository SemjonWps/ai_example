package de.wps.dddschulung.kartenverkauf.persistence.repositories;

import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;
import de.wps.dddschulung.kartenverkauf.persistence.mappers.SaalplanMapper;
import de.wps.dddschulung.kartenverkauf.persistence.mappers.SaalplanMapperImpl;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class SaalplanStapelImpl implements SaalplanStapel {
    private final SaalplanRepository saalplanRepository;
    private final SaalplanMapper saalplanMapper = new SaalplanMapperImpl();

    public Saalplan holeSaalplan(Vorstellung vorstellung) {
        SaalplanEntity saalplanEntity = saalplanRepository.findBySaalAndAnfangszeit(vorstellung.saal().name(), vorstellung.anfangszeit().zeitpunkt());
        return saalplanMapper.saalplanEntityToSaalplan(saalplanEntity);
    }

    public void legeZurueck(Saalplan saalplan) {

    }
}
