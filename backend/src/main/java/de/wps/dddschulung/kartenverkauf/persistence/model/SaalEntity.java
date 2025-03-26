package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class SaalEntity {
    @Id
    private Long id;
    private String name;
}
