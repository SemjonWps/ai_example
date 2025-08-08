package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.SaalplanEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Reservierungsnummer;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.TreeMap;

@Mapper
public interface SaalplanMapper {

    @Mapping(target = "id", expression = "java(saalplanId)")
    @Mapping(target = "vorstellungUUID", source = "saalplan.vorstellungId.uuid")
    @Mapping(target = "plaetze", source = "saalplan.plaetze", qualifiedByName = "PlaetzeToPlatzEntities")
    SaalplanEntity saalplanToSaalplanEntity(Saalplan saalplan, @Context int saalplanId);

    @Mapping(target = "vorstellungId.uuid", source = "vorstellungUUID")
    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlatzEntitiesToPlaetze")
    Saalplan saalplanEntityToSaalplan(SaalplanEntity saalplanEntity);

    @Named("PlaetzeToPlatzEntities")
    default List<PlatzEntity> mapPlaetzeToPlatzEntities(TreeMap<ReiheNummer, TreeMap<PlatzNummer, Platz>> plaetze, @Context int saalplanId) {
        return plaetze.values().stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .map(p -> platzToPlatzEntity(p, saalplanId))
                .toList();
    }

    @Named("PlatzEntitiesToPlaetze")
    default List<Platz> mapPlatzEntitiesToPlaetze(List<PlatzEntity> platzEntities) {
        return platzEntities.stream().map(this::platzEntityToPlatz).toList();
    }

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
