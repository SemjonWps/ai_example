package de.wps.dddschulung.kartenverkauf.mapper;

import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Platz;
import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Saal;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalEntity;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SaalplanMapper {

    PlatzMapper platzMapper = new PlatzMapperImpl();

    @Mapping(target = "anfangszeit", source = "vorstellung", qualifiedByName = "VorstellungToLocalDateTime")
    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlaetzeToPlatzEntities")
    @Mapping(target = "saal", source = "saal", qualifiedByName = "SaalToSaalEntity")
    SaalplanEntity saalplanToSaalplanEntity(Saalplan saalplan);

    @Mapping(target = "vorstellung", source = "saalplanEntity", qualifiedByName = "SaalplanEntityToVorstellung")
    @Mapping(target = "plaetze", source = "plaetze", qualifiedByName = "PlatzEntitiesToPlaetze")
    @Mapping(target = "saal", source = "saal", qualifiedByName = "SaalEntityToSaal")
    Saalplan saalplanEntityToSaalplan(SaalplanEntity saalplanEntity);

    @Named("VorstellungToLocalDateTime")
    default LocalDateTime mapVorstellungToLocalDateTime(Vorstellung vorstellung) {
        return vorstellung == null ? null : vorstellung.anfangszeit().anfangszeit();
    }

    @Named("SaalplanEntityToVorstellung")
    default Vorstellung mapSaalplanEntityToVorstellung(SaalplanEntity saalplanEntity) {
        return saalplanEntity == null ? null : new Vorstellung(
                new Saal(saalplanEntity.getSaal().getId(), saalplanEntity.getSaal().getName()),
                new Beginn(saalplanEntity.getAnfangszeit()),
                new Filmname(saalplanEntity.getOriginalTitel()));
    }

    @Named("PlaetzeToPlatzEntities")
    default List<PlatzEntity> mapPlatzEntities(List<Platz> plaetze) {
        List<PlatzEntity> platzEntities = new ArrayList<>();
        for (Platz platz : plaetze) {
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

    @Named("SaalToSaalEntity")
    default SaalEntity mapSaalToSaalEntity(Saal saal) {
        return saal == null ? null : new SaalEntity(saal.id(), saal.name());
    }

    @Named("SaalEntityToSaal")
    default Saal mapSaalEntityToSaal(SaalEntity saalEntity) {
        return saalEntity == null ? null : new Saal(saalEntity.getId(), saalEntity.getName());
    }
}
