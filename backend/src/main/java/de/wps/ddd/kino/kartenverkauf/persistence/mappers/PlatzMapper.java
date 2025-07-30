package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.Nullable;

@Mapper
public interface PlatzMapper {

    @Mapping(target = "platzId.reiheNr.nummer", source = "id.reiheNr")
    @Mapping(target = "platzId.platzNr.nummer", source = "id.platzNr")
    Platz platzEntityToPlatz(PlatzEntity platzEntity);

    @Mapping(target = "id.saalplanId", expression = "java(saalplanId)")
    @Mapping(target = "id.reiheNr", source = "platz.platzId.reiheNr.nummer")
    @Mapping(target = "id.platzNr", source = "platz.platzId.platzNr.nummer")
    @Mapping(target = "reservierungsnummer", source = "platz.reservierungsnummer.nummer")
    PlatzEntity platzToPlatzEntity(Platz platz, @Context int saalplanId);

    default Reservierungsnummer mapReservierungsnummer(@Nullable String reservierungsnummer) {
        return reservierungsnummer == null ? null : new Reservierungsnummer(reservierungsnummer);
    }
}
