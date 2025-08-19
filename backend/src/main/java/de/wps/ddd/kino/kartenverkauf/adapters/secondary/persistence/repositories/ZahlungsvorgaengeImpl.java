package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.mappers.ZahlungsvorgangEntityMapper;
import de.wps.ddd.kino.kartenverkauf.application.domain.entities.Zahlungsvorgang;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.application.ports.secondary.Zahlungsvorgaenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ZahlungsvorgaengeImpl implements Zahlungsvorgaenge {

    private final ZahlungsvorgangRepository zahlungsvorgangRepository;

    private final ZahlungsvorgangEntityMapper zahlungsvorgangMapper;

    @Override
    public void speichere(Zahlungsvorgang zahlungsvorgang) {
        var entity = zahlungsvorgangMapper.toEntity(zahlungsvorgang);
        zahlungsvorgangRepository.save(entity);
    }

    @Override
    public Zahlungsvorgang hole(Auftragsnummer auftragsnummer) {
        var entity = zahlungsvorgangRepository.findById(auftragsnummer.nummer()).orElseThrow();
        return zahlungsvorgangMapper.toDomain(entity);
    }

}
