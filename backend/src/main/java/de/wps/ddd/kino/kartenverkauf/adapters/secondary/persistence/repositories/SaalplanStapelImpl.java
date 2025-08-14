package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.SaalplanMapper;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.SaalplanEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.SaalplanStapel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaalplanStapelImpl implements SaalplanStapel {

    private final SaalplanRepository saalplanRepository;
    private final SaalplanMapper saalplanMapper;

    public Saalplan holeSaalplan(VorstellungId vorstellungId) {
        SaalplanEntity saalplanEntity = saalplanRepository.findByVorstellungUUID(vorstellungId.uuid());
        return saalplanMapper.toDomain(saalplanEntity);
    }

    public void legeZurueck(Saalplan saalplan) {
        Integer saalplanEntityId = saalplanRepository.findIdByVorstellungUUID(saalplan.getVorstellungId().uuid()).orElse(null);
        SaalplanEntity saalplanEntity = saalplanMapper.toEntity(saalplan, saalplanEntityId);
        saalplanRepository.save(saalplanEntity);
    }
}
