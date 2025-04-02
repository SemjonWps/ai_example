package de.wps.dddschulung.kartenverkauf.persistence.mapper;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.*;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface SaalplanMapper {

    PlatzMapper platzMapper = new PlatzMapperImpl();

    @Mapping(target = "anfangszeit", source = "vorstellung", qualifiedByName = "VorstellungToLocalDateTime")
    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlaetzeToPlatzEntities")
    @Mapping(target = "saal", source = "vorstellung", qualifiedByName = "VorstellungToSaalEntity")
    @Mapping(target = "originalTitel", source = "vorstellung", qualifiedByName = "VorstellungToOriginalTitel")
    SaalplanEntity saalplanToSaalplanEntity(Saalplan saalplan);

    @Mapping(target = "vorstellung", source = "saalplanEntity", qualifiedByName = "SaalplanEntityToVorstellung")
    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlatzEntitiesToPlaetze")
    Saalplan saalplanEntityToSaalplan(SaalplanEntity saalplanEntity);

    @Named("VorstellungToOriginalTitel")
    default String mapVorstellungToOriginalTitel(Vorstellung vorstellung) {
        return vorstellung == null ? null : vorstellung.filmname().originalTitel();
    }

    @Named("VorstellungToLocalDateTime")
    default LocalDateTime mapVorstellungToLocalDateTime(Vorstellung vorstellung) {
        return vorstellung == null ? null : vorstellung.anfangszeit().zeitpunkt();
    }

    @Named("SaalplanEntityToVorstellung")
    default Vorstellung mapSaalplanEntityToVorstellung(SaalplanEntity saalplanEntity) {
        return saalplanEntity == null ? null : new Vorstellung(
                new Saal(saalplanEntity.getSaal().getName()),
                new Beginn(saalplanEntity.getAnfangszeit()),
                new Filmname(saalplanEntity.getOriginalTitel()));
    }

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

    @Named("VorstellungToSaalEntity")
    default SaalEntity mapVorstellungToSaalEntity(Vorstellung vorstellung) {
        return vorstellung == null ? null : new SaalEntity(vorstellung.saal(), vorstellung.saal().name());
    }
}
