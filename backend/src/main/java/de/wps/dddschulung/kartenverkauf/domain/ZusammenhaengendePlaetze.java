package de.wps.dddschulung.kartenverkauf.domain;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ZusammenhaengendePlaetze {
    private Long id;
    List<Platz> plaetze;
}
