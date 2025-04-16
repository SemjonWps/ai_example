package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.VorstellungDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Beginn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface VorstellungDtoMapper {
    @Mapping(source = "anfangszeit", target = "anfangszeit", qualifiedByName = "beginnToString")
    @Mapping(source = "saal.name", target = "saal")
    @Mapping(source = "filmname.originalTitel", target = "filmname")
    VorstellungDto vorstellungToVorstellungDto(Vorstellung vorstellung);

    @Named("beginnToString")
    default String beginToString(Beginn begin) {
        return begin.zeitpunkt().toString();
    }
}
