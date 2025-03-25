package de.wps.dddschulung.kartenverkauf.domain;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class Vorstellung {
    @Id
    private Long id;
    @OneToOne
    @JoinColumn(name = "saal_id")
    private Saal saalId;
    private LocalDateTime anfangszeit;
}
