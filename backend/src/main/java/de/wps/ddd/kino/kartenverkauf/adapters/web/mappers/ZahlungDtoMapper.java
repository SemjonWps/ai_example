package de.wps.ddd.kino.kartenverkauf.adapters.web.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.web.model.GeldbetragDto;
import de.wps.ddd.kino.kartenverkauf.adapters.web.model.ZahlungsstatusDto;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsstatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {VorstellungDtoMapper.class, SaalplanDtoMapper.class})
public interface ZahlungDtoMapper {

    GeldbetragDto toDto(Geldbetrag geldbetrag);

    @Mapping(source = "status", target = "status")
    ZahlungsstatusDto toDto(Zahlungsstatus status);
}
