package de.wps.dddschulung.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vorstellungen")
@Data
@NoArgsConstructor
public class Vorstellung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime anfangszeit;
    private LocalDateTime endzeit;
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
    private Integer preis;
    @ManyToOne
    @JoinColumn(name = "saal_id", nullable = false)
    private Saal saal;

}
