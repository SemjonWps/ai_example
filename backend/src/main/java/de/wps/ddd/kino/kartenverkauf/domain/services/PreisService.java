package de.wps.ddd.kino.kartenverkauf.domain.services;

import de.wps.ddd.kino.kartenverkauf.domain.repositories.Vorstellungen;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreisService {

    private final Vorstellungen vorstellungen;

    public Geldbetrag ermittlePreis(UUID vorstellungUuid, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        var vorstellung = vorstellungen.holeVorstellung(vorstellungUuid);
        return vorstellung.getEintrittspreis().mal(zusammenhaengendePlaetze.anzahl());
    }
}
