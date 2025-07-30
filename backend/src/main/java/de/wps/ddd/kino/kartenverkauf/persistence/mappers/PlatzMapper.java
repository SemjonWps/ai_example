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

    @Mapping(target = "id.reihe.nummer", source = "id.reihe")
    @Mapping(target = "id.platz.nummer", source = "id.platz")
    Platz platzEntityToPlatz(PlatzEntity platzEntity);

    @Mapping(target = "id.saalplanId", expression = "java(saalplanId)")
    @Mapping(target = "id.reihe", source = "platz.id.reihe.nummer")
    @Mapping(target = "id.platz", source = "platz.id.platz.nummer")
    @Mapping(target = "reservierung", source = "platz.reservierung.nummer")
    PlatzEntity platzToPlatzEntity(Platz platz, @Context int saalplanId);

    default Reservierungsnummer mapReservierungsnummer(@Nullable String reservierungsnummer) {
        return reservierungsnummer == null ? null : new Reservierungsnummer(reservierungsnummer);
    }
}
