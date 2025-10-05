package de.wps.ddd.kino.filmauswahl.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@org.jmolecules.ddd.annotation.Repository
@org.springframework.stereotype.Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

    @Query("SELECT f.titel FROM Film f WHERE f.id = :id")
    String findTitleById(@Param("id") Long id);
}
