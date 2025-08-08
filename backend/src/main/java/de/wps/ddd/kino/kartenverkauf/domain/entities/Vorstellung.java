package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Saal;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

@AggregateRoot
@Getter
@AllArgsConstructor
public class Vorstellung {
    @Identity
    private final VorstellungId id;
    private final Saal saal;
    private final Beginn beginn;
    private final Filmname film;
    private final Geldbetrag eintrittspreis;
}
