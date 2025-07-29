package de.wps.ddd.kino.kartenverkauf.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.Vorstellungen;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.persistence.mappers.VorstellungMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VorstellungenImpl implements Vorstellungen {

    private final VorstellungRepository vorstellungRepository;

    private final VorstellungMapper vorstellungMapper;

    @Override
    public Vorstellung holeVorstellung(VorstellungId vorstellungId) {
        var vorstellung = vorstellungRepository.findById(vorstellungId.uuid());
        return vorstellung.map(vorstellungMapper::toDomain).orElseThrow();
    }
}
