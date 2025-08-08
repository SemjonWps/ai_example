package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.ZahlungMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Zahlungsvorgang;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.Zahlungsvorgaenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZahlungsvorgaengeImpl implements Zahlungsvorgaenge {

    private final ZahlungsvorgangRepository zahlungsvorgangRepository;

    private final ZahlungMapper zahlungMapper;

    @Override
    public void speichere(Zahlungsvorgang zahlungsvorgang) {
        var entity = zahlungMapper.toEntity(zahlungsvorgang);
        zahlungsvorgangRepository.save(entity);
    }

    @Override
    public Zahlungsvorgang hole(Auftragsnummer auftragsnummer) {
        var entity = zahlungsvorgangRepository.findById(auftragsnummer.nummer()).orElseThrow();
        return zahlungMapper.toDomain(entity);
    }

}
