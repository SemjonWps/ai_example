package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Kinokarte {
    Vorstellung vorstellung;
    PlatzId platz;
    Geldbetrag preis;
}
