package de.wps.ddd.kino.kartenverkauf.adapters.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.adapters.persistence.model.SaalplanEntity;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import lombok.Setter;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.TreeMap;

@Mapper(uses = {PlatzMapper.class})
public abstract class SaalplanMapper {

    @Autowired
    @Setter
    private PlatzMapper platzMapper;

    @Mapping(target = "id", expression = "java(saalplanId)")
    @Mapping(target = "vorstellungUUID", source = "saalplan.vorstellungId.uuid")
    @Mapping(target = "plaetze", source = "saalplan.plaetze", qualifiedByName = "PlaetzeToPlatzEntities")
    public abstract SaalplanEntity saalplanToSaalplanEntity(Saalplan saalplan, @Context int saalplanId);

    @Mapping(target = "vorstellungId.uuid", source = "vorstellungUUID")
    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlatzEntitiesToPlaetze")
    public abstract Saalplan saalplanEntityToSaalplan(SaalplanEntity saalplanEntity);

    @Named("PlaetzeToPlatzEntities")
    protected List<PlatzEntity> mapPlaetzeToPlatzEntities(TreeMap<ReiheNummer, TreeMap<PlatzNummer, Platz>> plaetze, @Context int saalplanId) {
        return plaetze.values().stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .map(p -> platzMapper.platzToPlatzEntity(p, saalplanId))
                .toList();
    }

    @Named("PlatzEntitiesToPlaetze")
    protected List<Platz> mapPlatzEntitiesToPlaetze(List<PlatzEntity> platzEntities) {
        return platzEntities.stream().map(platzMapper::platzEntityToPlatz).toList();
    }
}
