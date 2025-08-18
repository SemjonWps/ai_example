package de.wps.ddd.kino.kartenverkauf.application.domain.entities;

import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.valueobjects.Saal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

@AggregateRoot
@Getter
@AllArgsConstructor
public class Kinokarte {
    @Identity
    private final Integer id; // TODO: Kinokarten-ID?
    private final Film film;
    private final Beginn beginn;
    private final Saal saal;
    private final ReiheNummer reihe;
    private final PlatzNummer platz;
}
