package de.wps.ddd.kino.filmauswahl.events;

import java.time.*;

public class FilmHinzugefuegtEvent {
    private Long uuid;
    private String titel;
    private Integer preis;
    private String saal;
    private LocalDateTime beginn;

    public FilmHinzugefuegtEvent(Long uuid, String titel, Integer preis, String saal, LocalDateTime beginn) {
        this.uuid = uuid;
        this.titel = titel;
        this.preis = preis;
        this.saal = saal;
        this.beginn = beginn;
    }

    public Long getUuid() {
        return uuid;
    }
    public String getTitel() {
        return titel;
    }
    public Integer getPreis() {
        return preis;
    }
    public String getSaal() {
        return saal;
    }
    public LocalDateTime getBeginn() {
        return beginn;
    }
}