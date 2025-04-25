package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.persistence.model.SaalplanEntity;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.TreeMap;

@Mapper
@AllArgsConstructor
public abstract class SaalplanMapper {

    private final PlatzMapper platzMapper = new PlatzMapperImpl(); // TODO inject

    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlaetzeToPlatzEntities")
    public abstract SaalplanEntity saalplanToSaalplanEntity(Saalplan saalplan);

    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlatzEntitiesToPlaetze")
    public abstract Saalplan saalplanEntityToSaalplan(SaalplanEntity saalplanEntity);

    @Named("PlaetzeToPlatzEntities")
    protected List<PlatzEntity> mapPlaetzeToPlatzEntities(TreeMap<ReiheNummer, TreeMap<PlatzNummer, Platz>> plaetze) {
        return plaetze.values().stream()
                .flatMap(innerMap -> innerMap.values().stream())
                .map(platzMapper::platzToPlatzEntity)
                .toList();
    }

    @Named("PlatzEntitiesToPlaetze")
    protected List<Platz> mapPlatzEntitiesToPlaetze(List<PlatzEntity> platzEntities) {
        return platzEntities.stream().map(platzMapper::platzEntityToPlatz).toList();
    }
}
