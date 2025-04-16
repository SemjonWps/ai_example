package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import org.mapstruct.Mapper;

@Mapper
public interface PlatzDtoMapper {
    PlatzDto platzToPlatzDto(Platz platz);
}
