package de.wps.dddschulung.kartenverkauf.domain;

import de.wps.dddschulung.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;

import java.util.List;

public class Angebot {
    private Geldbetrag gesamtpreis;
    private SitzplatzStatus[][] saalplanBestuhlung;
    private Reihe reihe;
    private List<Sitz> zusammenhaengendePlaetze;
}
