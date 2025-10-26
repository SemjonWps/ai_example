package de.wps.ddd.kino.kartenverkauf.application.domain.kartenausstellung;

import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Beginn;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Film;
import de.wps.ddd.kino.kartenverkauf.application.domain.programm.Saal;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.application.domain.sitzplatzvergabe.ReiheNummer;
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
