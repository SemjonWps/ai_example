package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.ddd.kino.kartenverkauf.persistence.model.SaalplanEntity;
import lombok.AllArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

@Mapper
@AllArgsConstructor
public abstract class SaalplanMapper {

    private final PlatzMapper platzMapper = new PlatzMapperImpl(); // TODO inject

    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlaetzeToPlatzEntities")
    public abstract SaalplanEntity saalplanToSaalplanEntity(Saalplan saalplan);

    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlatzEntitiesToPlaetze")
    public abstract Saalplan saalplanEntityToSaalplan(SaalplanEntity saalplanEntity);

    @Named("PlaetzeToPlatzEntities")
    protected List<PlatzEntity> mapPlaetzeToPlatzEntities(SortedMap<Reihennummer, SortedMap<Platznummer, Platz>> plaetze) {
        List<PlatzEntity> platzEntities = new ArrayList<>();
        for (Platz platz : plaetze.values().stream().flatMap(innerMap -> innerMap.values().stream()).toList()) {
            platzEntities.add(platzMapper.platzToPlatzEntity(platz));
        }
        return platzEntities;
    }

    @Named("PlatzEntitiesToPlaetze")
    protected List<Platz> mapPlatzEntitiesToPlaetze(List<PlatzEntity> platzEntities) {
        List<Platz> plaetze = new ArrayList<>();
        for (PlatzEntity platzEntity : platzEntities) {
            plaetze.add(platzMapper.platzEntityToPlatz(platzEntity));
        }
        return plaetze;
    }
}
