package de.wps.ddd.kino.kartenverkauf.api.model;

import de.wps.ddd.kino.kartenverkauf.domain.Platzbelegungen;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Geldbetrag;

import java.util.List;

public record AngebotDto(Geldbetrag gesamtpreis, Platzbelegungen platzbelegungen, List<PlatzDto> platzDtos) {
}
