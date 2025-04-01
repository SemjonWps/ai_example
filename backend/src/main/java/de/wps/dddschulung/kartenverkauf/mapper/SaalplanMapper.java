package de.wps.dddschulung.kartenverkauf.mapper;

import de.wps.dddschulung.kartenverkauf.domain.domainobjects.Saalplan;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import org.mapstruct.Mapper;

@Mapper
public interface SaalplanMapper {
    SaalplanEntity saalplanToSaalplanEntity(Saalplan saalplan);

    Saalplan saalplanEntityToSaalplan(SaalplanEntity saalplanEntity);

}
