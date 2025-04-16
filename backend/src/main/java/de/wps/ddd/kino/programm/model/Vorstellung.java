package de.wps.ddd.kino.programm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vorstellungen", schema = "programm")
@Data
@NoArgsConstructor
public class Vorstellung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private UUID uuid;
    @JsonIgnore
    private Long filmId;
    private LocalDateTime anfangszeit;
    private Integer preis;
    @ManyToOne
    @JoinColumn(name = "saal_id", nullable = false)
    private Saal saal;
}
