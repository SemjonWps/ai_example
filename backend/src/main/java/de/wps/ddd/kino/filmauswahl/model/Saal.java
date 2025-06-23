package de.wps.ddd.kino.filmauswahl.model;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "saele", schema = "programm")
@Data
@NoArgsConstructor
public class Saal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonValue
    private String name;
}
