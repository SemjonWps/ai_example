package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.GeldbetragDto;
import de.wps.ddd.kino.kartenverkauf.api.model.ZahlunsanforderungDto;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsanforderung;
import org.mapstruct.Mapper;

@Mapper(uses = {VorstellungDtoMapper.class, PlatzDtoMapper.class})
public interface ZahlungDtoMapper {

    ZahlunsanforderungDto toDto(Zahlungsanforderung zahlunsanforderung);

    GeldbetragDto toDto(Geldbetrag geldbetrag);
}
