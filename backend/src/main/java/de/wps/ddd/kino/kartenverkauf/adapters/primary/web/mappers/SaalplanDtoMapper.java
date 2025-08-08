package de.wps.ddd.kino.kartenverkauf.adapters.primary.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.PlatzIdDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.adapters.primary.web.model.ZusammenhaengendePlaetzeDto;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzId;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ZusammenhaengendePlaetze;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Map;

@Mapper
public interface SaalplanDtoMapper {

    default SaalplanDto saalplantoSaalplanDto(Saalplan saalplan) {
        var plaetze = saalplan.getPlaetze();

        var reihenzahl = plaetze.size();
        var ersteReihe = plaetze.values().stream().findFirst();
        var platzAnzahl = ersteReihe.map(Map::size).orElse(0);
        var platzbelegungen = new PlatzDto[reihenzahl][platzAnzahl];

        plaetze.forEach((reiheNr, plaetzeListe) -> plaetzeListe.forEach((platzNr, platz) ->
                platzbelegungen[reiheNr.nummer() - 1][platzNr.nummer() - 1] = toDto(platz)
        ));

        return new SaalplanDto(platzbelegungen);
    }

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
