package de.wps.ddd.kino.filmauswahl.model;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;

@org.springframework.stereotype.Repository
public interface FilmRepository extends Repository<Film, Long> {
    // Vorstellungen gruppiert nach Filmen
    @Query("select f from Film f join fetch f.vorstellungen v where v.anfangszeit >= :start and v.anfangszeit < :end")
    List<Film> findFilmsBetween(LocalDateTime start, LocalDateTime end);
}
