package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Saal {
    private Long id;
    private String name;

    public String getName() {
        return name;
    }
}
