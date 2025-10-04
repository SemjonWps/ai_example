package de.wps.ddd.kino.saalkonfiguration.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jmolecules.ddd.annotation.Identity;

@org.jmolecules.ddd.annotation.Entity
@Entity
@Table(name = "saele", schema = "saalkonfiguration")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaalConfiguration {
    @Identity
    @Id
    private String name;
    private int reihen;
    private int spalten;
}
