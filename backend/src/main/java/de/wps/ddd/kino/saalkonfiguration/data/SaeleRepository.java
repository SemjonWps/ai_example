package de.wps.ddd.kino.saalkonfiguration.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@org.jmolecules.ddd.annotation.Repository
@org.springframework.stereotype.Repository
public interface SaeleRepository extends CrudRepository<SaalConfiguration, String> {
    List<SaalConfiguration> findAll();
    Optional<SaalConfiguration> findByName(String name);
    void deleteByName(String name);
}
