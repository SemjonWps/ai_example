package de.wps.dddschulung.kartenverkauf.persistence.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "saalplaene", schema = "kartenverkauf", uniqueConstraints = @UniqueConstraint(name = "unique_anfangszeit_saal", columnNames = {"anfangszeit", "saal"}))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaalplanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String originalTitel;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "saalplan_plaetze",
            schema = "kartenverkauf",
            joinColumns = @JoinColumn(name = "saalplan_id"),
            inverseJoinColumns = @JoinColumn(name = "plaetze_id")
    )
    private List<PlatzEntity> plaetze;
    @OneToOne
    private VorstellungEntity vorstellung;
}
