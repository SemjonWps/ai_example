package de.wps.dddschulung.kartenverkauf.persistence.mappers;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Vorstellung;
import de.wps.dddschulung.kartenverkauf.persistence.model.VorstellungEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VorstellungMapper {
    @Mapping(target = "anfangszeit", source = "anfangszeit.zeitpunkt")
    @Mapping(target = "saal", source = "saal.name")
    VorstellungEntity vorstellungToVorstellungEntity(Vorstellung vorstellung);

    @Mapping(target = "anfangszeit.zeitpunkt", source = "anfangszeit")
    @Mapping(target = "saal.name", source = "saal")
    Vorstellung vorstellungEntityToVorstellung(VorstellungEntity vorstellungEntity);

}
