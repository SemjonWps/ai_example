package de.wps.ddd.kino.kartenverkauf.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.persistence.model.VorstellungEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

@org.springframework.stereotype.Repository
public interface VorstellungRepository extends JpaRepository<VorstellungEntity, UUID> {
    @Query("SELECT v.eintrittspreis FROM VorstellungEntity v WHERE v.uuid = :uuid")
    int findEintrittspreisByUuid(UUID uuid);
}
