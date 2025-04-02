package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vorstellungen", schema = "kartenverkauf")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VorstellungEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
