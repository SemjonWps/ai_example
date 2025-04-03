package de.wps.dddschulung.kartenverkauf.persistence.repositories;

import de.wps.dddschulung.kartenverkauf.persistence.model.SaalplanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaalplanRepository extends JpaRepository<SaalplanEntity, Long> {
    //SaalplanEntity findBySaalAndAnfangszeit(String saal, LocalDateTime anfangszeit);
}
