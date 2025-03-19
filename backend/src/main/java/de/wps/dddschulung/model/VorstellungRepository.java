package de.wps.dddschulung.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VorstellungRepository extends JpaRepository<Vorstellung, Long> {

    List<Vorstellung> findAllByOrderByAnfangszeitAsc();
    List<Vorstellung> findByAnfangszeitBetween(LocalDateTime start, LocalDateTime end);
}
