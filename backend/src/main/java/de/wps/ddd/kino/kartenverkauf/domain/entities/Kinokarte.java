package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Saal;
import lombok.AllArgsConstructor;
import lombok.Getter;

// @AggregateRoot
@Getter
@AllArgsConstructor
public class Kinokarte {
    // TODO: Kinokarten-ID?
    private final Filmname film;
    private final Beginn beginn;
    private final Saal saal;
    private final ReiheNummer reihe;
    private final PlatzNummer platz;
}
