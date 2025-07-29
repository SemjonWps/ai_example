package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Beginn;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Filmname;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Saal;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.VorstellungId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Vorstellung {
    private final VorstellungId id;
    private final Saal saal;
    private final Beginn anfangszeit;
    private final Filmname filmname;
    private final Geldbetrag eintrittspreis;
}
