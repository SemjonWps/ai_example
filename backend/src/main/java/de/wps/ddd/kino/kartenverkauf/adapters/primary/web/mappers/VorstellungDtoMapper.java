package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Vorstellung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VorstellungDtoMapper {
    @Mapping(source = "id.uuid", target = "uuid")
    @Mapping(source = "beginn.zeitpunkt", target = "beginn")
    @Mapping(source = "saal.name", target = "saal")
    @Mapping(source = "film.name", target = "film")
    VorstellungDto toDto(Vorstellung vorstellung);

}
