package de.wps.ddd.kino.kartenverkauf.domain.services;

import de.wps.ddd.kino.kartenverkauf.domain.repositories.AktuelleVorstellungen;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ZusammenhaengendePlaetze;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreisService {

    private final AktuelleVorstellungen vorstellungen;

    public Geldbetrag ermittlePreis(VorstellungId vorstellungId, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        var vorstellung = vorstellungen.holeVorstellung(vorstellungId);
        return vorstellung.getEintrittspreis().mal(zusammenhaengendePlaetze.anzahl());
    }
}
