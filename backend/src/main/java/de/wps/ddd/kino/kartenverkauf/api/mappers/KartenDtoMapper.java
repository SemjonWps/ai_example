package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.KinokarteDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Kinokarte;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = {VorstellungDtoMapper.class, PlatzDtoMapper.class, ZahlungDtoMapper.class})
public interface KartenDtoMapper {

    List<KinokarteDto> toDto(List<Kinokarte> kinokarte);

    KinokarteDto toDto(Kinokarte kinokarte);
}
