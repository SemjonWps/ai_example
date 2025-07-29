package de.wps.ddd.kino.kartenverkauf.api.model;

import java.util.UUID;

public record VorstellungDto(UUID uuid, String anfangszeit, String saal, String filmname) {
}
