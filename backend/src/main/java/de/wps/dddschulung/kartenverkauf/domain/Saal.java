package de.wps.dddschulung.kartenverkauf.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Saal {
    @Id
    private Long id;
    private String name;
}
