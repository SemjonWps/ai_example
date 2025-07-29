package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VorstellungDtoMapper {
    @Mapping(source = "id.uuid", target = "uuid")
    @Mapping(source = "anfangszeit.zeitpunkt", target = "anfangszeit")
    @Mapping(source = "saal.name", target = "saal")
    @Mapping(source = "filmname.originalTitel", target = "filmname")
    VorstellungDto toDto(Vorstellung vorstellung);

}
