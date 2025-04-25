package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.api.model.PlatzIdDto;
import de.wps.ddd.kino.kartenverkauf.api.model.ZusammenhaengendePlaetzeDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PlatzDtoMapper {

    ZusammenhaengendePlaetzeDto toDto(ZusammenhaengendePlaetze plaetze);

    ZusammenhaengendePlaetze toDomain(ZusammenhaengendePlaetzeDto plaetze);

    @Mapping(target = "platzNr", source = "platzId.platzNr.nummer")
    @Mapping(target = "reiheNr", source = "platzId.reiheNr.nummer")
    @Mapping(target = "istFrei", source = "platz", qualifiedByName = "mapIstFrei")
    PlatzDto toDto(Platz platz);

    @Named("mapIstFrei")
    static boolean mapIstFrei(Platz platz) {
        return platz.istFrei();
    }

    @Mapping(target = "reiheNr.nummer", source = "platzIdDto.reiheNr")
    @Mapping(target = "platzNr.nummer", source = "platzIdDto.platzNr")
    PlatzId toDomain(PlatzIdDto platzIdDto);

    @Mapping(target = "platzNr", source = "platzNr.nummer")
    @Mapping(target = "reiheNr", source = "reiheNr.nummer")
    PlatzIdDto toDto(PlatzId platzId);
}
