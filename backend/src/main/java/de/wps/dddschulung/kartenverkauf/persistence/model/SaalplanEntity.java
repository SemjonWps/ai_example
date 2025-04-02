package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "saalplaene", uniqueConstraints = @UniqueConstraint(name = "unique_anfangszeit_saal", columnNames = {"anfangszeit", "saal"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaalplanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime anfangszeit;
    private String originalTitel;
    @OneToMany
    private List<PlatzEntity> plaetze;
    private String saal;
}
