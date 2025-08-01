package de.wps.ddd.kino.kartenverkauf.adapters.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.web.model.GeldbetragDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.ZahlunsanforderungDto;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsanforderung;
import org.mapstruct.Mapper;

@Mapper(uses = {VorstellungDtoMapper.class, PlatzDtoMapper.class})
public interface ZahlungDtoMapper {

    ZahlunsanforderungDto toDto(Zahlungsanforderung zahlunsanforderung);

    GeldbetragDto toDto(Geldbetrag geldbetrag);
}
