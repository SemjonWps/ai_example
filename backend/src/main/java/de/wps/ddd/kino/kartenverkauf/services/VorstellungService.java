package de.wps.ddd.kino.kartenverkauf.services;

import de.wps.ddd.kino.kartenverkauf.api.mappers.VorstellungDtoMapper;
import de.wps.ddd.kino.kartenverkauf.api.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.domain.repositories.Vorstellungen;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class VorstellungService {
    private final Vorstellungen vorstellungen;
    private final VorstellungDtoMapper vorstellungDtoMapper;

    public VorstellungDto holeVorstellung(String vorstellungUuid) {
        return vorstellungDtoMapper.vorstellungToVorstellungDto(vorstellungen.holeVorstellung(UUID.fromString(vorstellungUuid)));
    }
}
