package de.wps.dddschulung.kartenverkauf.persistence;

import de.wps.dddschulung.kartenverkauf.domain.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;
import de.wps.dddschulung.kartenverkauf.mapper.SaalMapper;
import de.wps.dddschulung.kartenverkauf.mapper.SaalMapperImpl;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class SaalplanStapelImpl implements SaalplanStapel {
    private final SaalplanRepository saalplanRepository;
    private final SaalMapper saalMapper = new SaalMapperImpl();

    public Saalplan holeSaalplan(Vorstellung vorstellung) {
        SaalEntity saalEntity = saalMapper.SaalToSaalEntity(vorstellung.saal());
        SaalplanEntity saalplanEntity = saalplanRepository.findBySaalAndAnfangszeit(saalEntity, vorstellung.anfangszeit().zeitpunkt());
        return null;
    }

    public void legeZurueck(Saalplan saalplan) {

    }
}
