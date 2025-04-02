package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vorstellungen", schema = "kartenverkauf")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VorstellungEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private UUID uuid;
    private LocalDateTime anfangszeit;
    private String saal;

}
