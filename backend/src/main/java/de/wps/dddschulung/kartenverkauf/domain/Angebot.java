package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Angebot {
    private Geldbetrag gesamtpreis;
    private Platzbelegungen platzbelegungen;
    private Reihe reihe;
    private ZusammenhaengendePlaetze zusammenhaengendePlaetze;
}
