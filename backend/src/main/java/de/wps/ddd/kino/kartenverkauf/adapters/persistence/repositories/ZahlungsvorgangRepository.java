package de.wps.ddd.kino.kartenverkauf.adapters.persistence.repositories;

import de.wps.ddd.kino.kartenverkauf.adapters.persistence.model.ZahlungsvorgangEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ZahlungsvorgangRepository extends JpaRepository<ZahlungsvorgangEntity, UUID> {
}
