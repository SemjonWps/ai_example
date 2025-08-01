package de.wps.ddd.kino.kartenverkauf.adapters.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.web.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
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
