package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.VorstellungMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.AktuelleVorstellungen;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AktuelleVorstellungenImpl implements AktuelleVorstellungen {

    private final VorstellungRepository vorstellungRepository;

    private final VorstellungMapper vorstellungMapper;

    @Override
    public Vorstellung holeVorstellung(VorstellungId vorstellungId) {
        var vorstellung = vorstellungRepository.findById(vorstellungId.uuid());
        return vorstellung.map(vorstellungMapper::toDomain).orElseThrow();
    }
}
