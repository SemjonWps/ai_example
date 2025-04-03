package de.wps.dddschulung.kartenverkauf.persistence.mappers;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface SaalplanMapper {

    PlatzMapper platzMapper = new PlatzMapperImpl();

    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlaetzeToPlatzEntities")
    SaalplanEntity saalplanToSaalplanEntity(Saalplan saalplan);

    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlatzEntitiesToPlaetze")
    Saalplan saalplanEntityToSaalplan(SaalplanEntity saalplanEntity);

    @Named("PlaetzeToPlatzEntities")
    default List<PlatzEntity> mapPlaetzeToPlatzEntities(Map<Reihe, List<Platz>> plaetze) {
        List<PlatzEntity> platzEntities = new ArrayList<>();
        for (Platz platz : plaetze.values().stream().flatMap(List::stream).toList()) {
            platzEntities.add(platzMapper.platzToPlatzEntity(platz));
        }
        return platzEntities;
    }

    @Named("PlatzEntitiesToPlaetze")
    default List<Platz> mapPlatzEntitiesToPlaetze(List<PlatzEntity> platzEntities) {
        List<Platz> plaetze = new ArrayList<>();
        for (PlatzEntity platzEntity : platzEntities) {
            plaetze.add(platzMapper.platzEntityToPlatz(platzEntity));
        }
        return plaetze;
    }
}
