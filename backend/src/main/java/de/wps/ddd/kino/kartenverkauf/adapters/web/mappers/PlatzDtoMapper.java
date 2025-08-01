package de.wps.ddd.kino.kartenverkauf.adapters.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.web.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.PlatzIdDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.ZusammenhaengendePlaetzeDto;
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

    @Mapping(target = "platz", source = "id.platz.nummer")
    @Mapping(target = "reihe", source = "id.reihe.nummer")
    @Mapping(target = "istFrei", source = "platz", qualifiedByName = "mapIstFrei")
    PlatzDto toDto(Platz platz);

    @Named("mapIstFrei")
    static boolean mapIstFrei(Platz platz) {
        return platz.istFrei();
    }

    @Mapping(target = "reihe.nummer", source = "platzIdDto.reihe")
    @Mapping(target = "platz.nummer", source = "platzIdDto.platz")
    PlatzId toDomain(PlatzIdDto platzIdDto);

    @Mapping(target = "platz", source = "platz.nummer")
    @Mapping(target = "reihe", source = "reihe.nummer")
    PlatzIdDto toDto(PlatzId platzId);
}
