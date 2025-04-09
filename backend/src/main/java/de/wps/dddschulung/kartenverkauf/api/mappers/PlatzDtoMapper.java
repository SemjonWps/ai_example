package de.wps.dddschulung.kartenverkauf.api.mappers;

import de.wps.dddschulung.kartenverkauf.api.model.PlatzDto;
import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import org.mapstruct.Mapper;

@Mapper
public interface PlatzDtoMapper {
    PlatzDto platzToPlatzDto(Platz platz);
}
