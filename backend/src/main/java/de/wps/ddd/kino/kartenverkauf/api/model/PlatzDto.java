package de.wps.ddd.kino.kartenverkauf.api.model;

import de.wps.ddd.kino.kartenverkauf.domain.enums.SitzplatzStatus;

public record PlatzDto(int reiheNr, int platzNr, SitzplatzStatus sitzplatzStatus) {
}
