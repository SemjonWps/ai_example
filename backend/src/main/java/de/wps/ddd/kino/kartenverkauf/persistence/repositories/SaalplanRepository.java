package de.wps.ddd.kino.kartenverkauf.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.persistence.model.SaalplanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SaalplanRepository extends JpaRepository<SaalplanEntity, Long> {
    SaalplanEntity findByVorstellungUUID(UUID vorstellungUUID);
}
