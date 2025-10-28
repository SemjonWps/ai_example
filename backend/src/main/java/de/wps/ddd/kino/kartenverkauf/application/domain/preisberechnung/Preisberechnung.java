package de.wps.ddd.kino.kartenverkauf.application.domain.preisberechnung;

import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.application.domain.filmauswahl.Vorstellung;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Platz;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.Saalplan;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ZusammenhaengendePlaetze;
import org.jmolecules.ddd.annotation.Service;
import org.springframework.stereotype.Component;

@Service
@Component
public class Preisberechnung {
    public Geldbetrag ermittlePreis(
            Vorstellung vorstellung,
            Saalplan saalplan,
            ZusammenhaengendePlaetze zusammenhaengendePlaetze) {

        var basispreis = this.basispreis(vorstellung, zusammenhaengendePlaetze);
        var zuschlagPlaetze = this.zuschlagPlaetze(saalplan, zusammenhaengendePlaetze);
        return basispreis.plus(zuschlagPlaetze);
    }

    private Geldbetrag basispreis(Vorstellung vorstellung, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        return vorstellung.getEintrittspreis().mal(zusammenhaengendePlaetze.anzahl().value());
    }

    private Geldbetrag zuschlagPlaetze(Saalplan saalplan, ZusammenhaengendePlaetze zusammenhaengendePlaetze) {
        return zusammenhaengendePlaetze.plaetze().stream()
                .map(saalplan::platz)
                .map(this::zuschlagPlatz)
                .reduce(Geldbetrag.euroInCent(0), Geldbetrag::plus);
    }

    private Geldbetrag zuschlagPlatz(Platz platz) {
        return switch (platz.getKategorie()) {
            case Parkett -> Geldbetrag.euro(0, 0);
            case Loge -> Geldbetrag.euro(2, 0);
        };
    }


}
