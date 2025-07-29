package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.KinokarteDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Kinokarte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface KartenDtoMapper {

    List<KinokarteDto> toDto(List<Kinokarte> kinokarte);

    @Mapping(source = "film.name", target = "film")
    @Mapping(source = "beginn.zeitpunkt", target = "beginn")
    @Mapping(source = "saal.name", target = "saal")
    @Mapping(source = "reihe.nummer", target = "reihe")
    @Mapping(source = "platz.nummer", target = "platz")
    KinokarteDto toDto(Kinokarte kinokarte);
}
