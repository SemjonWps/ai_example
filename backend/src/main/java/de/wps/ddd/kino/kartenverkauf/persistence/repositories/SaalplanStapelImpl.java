package de.wps.ddd.kino.kartenverkauf.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.persistence.mappers.SaalplanMapper;
import de.wps.ddd.kino.kartenverkauf.persistence.model.SaalplanEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaalplanStapelImpl implements SaalplanStapel {
    private final SaalplanRepository saalplanRepository;
    private final SaalplanMapper saalplanMapper;

    public Saalplan holeSaalplan(VorstellungId vorstellungId) {
        SaalplanEntity saalplanEntity = saalplanRepository.findByVorstellungUUID(vorstellungId.uuid());
        return saalplanMapper.saalplanEntityToSaalplan(saalplanEntity);
    }

    public void legeZurueck(Saalplan saalplan) {
        SaalplanEntity saalplanEntity = saalplanMapper.saalplanToSaalplanEntity(saalplan);
        saalplanRepository.save(saalplanEntity);
    }
}
