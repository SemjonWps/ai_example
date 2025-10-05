package de.wps.ddd.kino.filmauswahl.data;

import org.springframework.data.jpa.repository.JpaRepository;

@org.jmolecules.ddd.annotation.Repository
@org.springframework.stereotype.Repository
public interface FilmauswahlVorstellungRepository extends JpaRepository<Vorstellung, Long> {
}
