package de.wps.dddschulung.kartenverkauf.persistence;

import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaalplanRepository extends JpaRepository<SaalplanEntity, Long> {
}
