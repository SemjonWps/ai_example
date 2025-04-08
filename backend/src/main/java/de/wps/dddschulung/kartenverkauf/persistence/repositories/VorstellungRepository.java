package de.wps.dddschulung.kartenverkauf.persistence.repositories;

import de.wps.dddschulung.kartenverkauf.domain.entities.Vorstellung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface VorstellungRepository extends JpaRepository<Vorstellung, Long> {
    @Query("SELECT v.eintrittspreis FROM VorstellungEntity v WHERE v.uuid = :uuid")
    int findEintrittspreisByUuid(UUID uuid);
}
