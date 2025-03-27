package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Vorstellung {
    private Long id;
    private Saal saal;
    private LocalDateTime anfangszeit;
}
