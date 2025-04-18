package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.api.model.PlatzIdDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PlatzDtoMapper {

    @Mapping(target = "platzNr", source = "platzId.platzNr.nummer")
    @Mapping(target = "reiheNr", source = "platzId.reiheNr.nummer")
    @Mapping(target = "sitzplatzStatus", source = "platz", qualifiedByName = "istBelegtToSitzplatzStatus")
    PlatzDto platzToPlatzDto(Platz platz);

    @Named("istBelegtToSitzplatzStatus")
    default SitzplatzStatus istBelegtToSitzplatzStatus(Platz platz) {
        return platz.istBelegt() ? SitzplatzStatus.BELEGT : SitzplatzStatus.FREI;
    }

    @Mapping(target = "platzNr", source = "platzNr.nummer")
    @Mapping(target = "reiheNr", source = "reiheNr.nummer")
    PlatzIdDto platzIdToPlatzIdDto(PlatzId platzId);
}
