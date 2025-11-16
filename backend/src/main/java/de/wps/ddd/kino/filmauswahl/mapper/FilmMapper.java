package de.wps.ddd.kino.filmauswahl.mapper;

import de.wps.ddd.kino.filmauswahl.data.Film;
import de.wps.ddd.kino.filmauswahl.web.FilmEingebenDto;
import org.springframework.stereotype.Component;

@Component
public class FilmMapper {

    /**
     * Maps a FilmEingebenDto to a Film entity
     *
     * @param filmDto the DTO containing film data
     * @return a new Film entity with data from the DTO
     */
    public Film toEntity(FilmEingebenDto filmDto) {
        Film film = new Film();
        film.setTitel(filmDto.getTitel());
        film.setLaufzeit(filmDto.getLaufzeit());
        film.setPosterUrl(filmDto.getPosterUrl());
        film.setFsk(filmDto.getFsk());
        film.setBeschreibung(filmDto.getBeschreibung());
        film.setGenre(filmDto.getGenre());
        film.setHauptdarsteller(filmDto.getHauptdarsteller());
        film.setRegie(filmDto.getRegie());
        film.setSprache(filmDto.getSprache());
        return film;
    }
}
