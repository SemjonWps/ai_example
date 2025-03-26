package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
public class Vorstellung {
    private Long id;
    private Saal saal;
    private LocalDateTime anfangszeit;
}
