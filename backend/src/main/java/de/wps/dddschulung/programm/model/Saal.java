package de.wps.dddschulung.programm.model;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
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
