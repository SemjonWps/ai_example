package de.wps.ddd.kino.kartenverkauf.adapters.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.persistence.mappers.ZahlungMapper;
import de.wps.ddd.kino.kartenverkauf.application.ports.out.Zahlungsvorgaenge;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Zahlungsvorgang;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Auftragsnummer;
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
