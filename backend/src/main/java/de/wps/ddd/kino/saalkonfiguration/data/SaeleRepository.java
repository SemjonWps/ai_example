package de.wps.ddd.kino.saalkonfiguration.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
@org.springframework.stereotype.Repository
public interface SaeleRepository extends CrudRepository<SaalKonfiguration, String> {
    List<SaalKonfiguration> findAll();
    Optional<SaalKonfiguration> findByName(String name);
    void deleteByName(String name);
}
