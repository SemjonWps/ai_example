package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "saalplaene", schema = "kartenverkauf", uniqueConstraints = @UniqueConstraint(name = "unique_anfangszeit_saal", columnNames = {"anfangszeit", "saal"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaalplanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "saalplan_id")
    private List<PlatzEntity> plaetze;
    private UUID vorstellungUUID;
}
