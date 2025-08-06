package de.wps.ddd.kino.kartenverkauf.adapters.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.persistence.model.ZahlungsvorgangEntity;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Zahlungsvorgang;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ZahlungMapper {

    @Mapping(target = "auftragsnummer", source = "auftragsnummer.nummer")
    ZahlungsvorgangEntity toEntity(Zahlungsvorgang zahlungsvorgang);

    @Mapping(target = "auftragsnummer.nummer", source = "auftragsnummer")
    Zahlungsvorgang toDomain(ZahlungsvorgangEntity zahlungsvorgangEntity);
}
