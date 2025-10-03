package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "saele", schema = "kartenverkauf")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaalEntity {
    @Id
    private String name;
    private int reihen;
    private int spalten;
}