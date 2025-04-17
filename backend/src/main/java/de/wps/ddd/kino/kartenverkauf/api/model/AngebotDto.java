package de.wps.ddd.kino.kartenverkauf.api.model;

import java.util.List;

public record AngebotDto(GeldbetragDto gesamtpreis, SaalplanDto saalplan, List<PlatzIdDto> angebotenePlaetze) {
}
