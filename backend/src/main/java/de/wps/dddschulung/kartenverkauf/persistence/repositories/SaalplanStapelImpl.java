package de.wps.dddschulung.kartenverkauf.persistence.repositories;

import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.persistence.mappers.SaalplanMapper;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class SaalplanStapelImpl implements SaalplanStapel {
    private final SaalplanRepository saalplanRepository;
    private final SaalplanMapper saalplanMapper;

    public Saalplan holeSaalplan(UUID vorstellungUUID) {
        SaalplanEntity saalplanEntity = saalplanRepository.findByVorstellungUUID(vorstellungUUID);
        return saalplanMapper.saalplanEntityToSaalplan(saalplanEntity);
    }

    public void legeZurueck(Saalplan saalplan) {
        SaalplanEntity saalplanEntity = saalplanMapper.saalplanToSaalplanEntity(saalplan);
        saalplanRepository.save(saalplanEntity);
    }
}
