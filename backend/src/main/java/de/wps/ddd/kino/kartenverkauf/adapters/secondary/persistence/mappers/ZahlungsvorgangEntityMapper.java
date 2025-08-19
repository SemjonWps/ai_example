package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model.ZahlungsvorgangEntity;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Zahlungsvorgang;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Zahlungsstatus;
import org.springframework.stereotype.Component;

@Component
public class ZahlungsvorgangEntityMapper {

    public ZahlungsvorgangEntity toEntity(Zahlungsvorgang zahlungsvorgang) {
        return new ZahlungsvorgangEntity(
                zahlungsvorgang.getAuftragsnummer().nummer(),
                zahlungsvorgang.getStatus().name()
        );
    }

    public Zahlungsvorgang toDomain(ZahlungsvorgangEntity zahlungsvorgangEntity) {
        return new Zahlungsvorgang(
                new Auftragsnummer(zahlungsvorgangEntity.getAuftragsnummer()),
                Zahlungsstatus.valueOf(zahlungsvorgangEntity.getStatus())
        );
    }
}
