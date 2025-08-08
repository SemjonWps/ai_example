package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "saalplaene", schema = "kartenverkauf")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaalplanEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID vorstellungUUID;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "saalplan_id")
    private List<PlatzEntity> plaetze;
}
