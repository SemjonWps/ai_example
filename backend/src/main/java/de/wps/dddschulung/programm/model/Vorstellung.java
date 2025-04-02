package de.wps.dddschulung.programm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vorstellungen", schema = "programm")
@Data
@NoArgsConstructor
public class Vorstellung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    private Long filmId;
    private LocalDateTime anfangszeit;
    private Integer preis;
    @ManyToOne
    @JoinColumn(name = "saal_id", nullable = false)
    private Saal saal;
}
