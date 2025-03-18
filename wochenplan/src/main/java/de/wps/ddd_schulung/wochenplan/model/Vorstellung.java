package de.wps.ddd_schulung.wochenplan.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "vorstellungen")
public class Vorstellung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime anfangszeit;
    private LocalDateTime endzeit;
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;
    private Float preis;
    @ManyToOne
    @JoinColumn(name = "saal_id", nullable = false)
    private Saal saal;

    public Vorstellung() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAnfangszeit() {
        return anfangszeit;
    }

    public void setAnfangszeit(LocalDateTime anfangszeit) {
        this.anfangszeit = anfangszeit;
    }

    public LocalDateTime getEndzeit() {
        return endzeit;
    }

    public void setEndzeit(LocalDateTime endzeit) {
        this.endzeit = endzeit;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Float getPreis() {
        return preis;
    }

    public void setPreis(Float preis) {
        this.preis = preis;
    }

    public Saal getSaal() {
        return saal;
    }

    public void setSaal(Saal saal) {
        this.saal = saal;
    }
}
