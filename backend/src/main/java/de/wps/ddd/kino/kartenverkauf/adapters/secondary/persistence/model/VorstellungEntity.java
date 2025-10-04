package de.wps.ddd.kino.kartenverkauf.adapters.secondary.persistence.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private UUID uuid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginn;
    private String saal;
    private String film;
    private int eintrittspreis;
}
