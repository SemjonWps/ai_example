package de.wps.ddd.kino.kartenverkauf.api.model;

import java.util.List;

public record AngebotDto(GeldbetragDto gesamtpreis, SaalplanDto saalplanDto, List<PlatzDto> platzDtos) {
}
