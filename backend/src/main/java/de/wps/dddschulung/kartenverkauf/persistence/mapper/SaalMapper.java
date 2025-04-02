package de.wps.dddschulung.kartenverkauf.persistence.mapper;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Saal;
import de.wps.dddschulung.kartenverkauf.persistence.model.SaalEntity;
import org.mapstruct.Mapper;

@Mapper
public interface SaalMapper {
    SaalEntity SaalToSaalEntity(Saal saal);
}
