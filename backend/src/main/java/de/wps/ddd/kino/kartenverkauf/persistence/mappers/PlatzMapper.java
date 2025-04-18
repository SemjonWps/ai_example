package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.PlatzNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.ReiheNummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PlatzMapper {
    @Mapping(target = "reservierungsnummer", source = "reservierungsnummer", qualifiedByName = "StringToReservierungsnummer")
    @Mapping(target = "platzId.reiheNr", source = "reiheNr", qualifiedByName = "IntToReiheNummer")
    @Mapping(target = "platzId.platzNr", source = "platzNr", qualifiedByName = "IntToPlatzNummer")
    Platz platzEntityToPlatz(PlatzEntity platzEntity);

    @Mapping(target = "reservierungsnummer", source = "reservierungsnummer", qualifiedByName = "ReservierungsnummerToString")
    @Mapping(target = "reiheNr", source = "platzId.reiheNr", qualifiedByName = "ReiheNummerToInt")
    @Mapping(target = "platzNr", source = "platzId.platzNr", qualifiedByName = "PlatzNummerToInt")
    PlatzEntity platzToPlatzEntity(Platz platz);

    @Named("ReservierungsnummerToString")
    default String mapReservierungsnummerToString(Reservierungsnummer reservierungsnummer) {
        return reservierungsnummer == null ? null : reservierungsnummer.reservierungsnummer();
    }

    @Named("StringToReservierungsnummer")
    default Reservierungsnummer mapStringToReservierungsnummer(String reservierungsnummer) {
        return reservierungsnummer == null ? null : new Reservierungsnummer(reservierungsnummer);
    }

    @Named("IntToReiheNummer")
    default ReiheNummer mapReihennummerToReihe(int reiheNr) {
        return new ReiheNummer(reiheNr);
    }

    @Named("ReiheNummerToInt")
    default int mapReiheToReiheNummer(ReiheNummer reiheNr) {
        if (reiheNr == null) {
            throw new IllegalArgumentException("ReiheNr must not be null");
        }
        return reiheNr.nummer();
    }

    @Named("IntToPlatzNummer")
    default PlatzNummer mapPlatzNummerToSitz(int platzNr) {
        return new PlatzNummer(platzNr);
    }

    @Named("PlatzNummerToInt")
    default int mapSitzToPlatznummer(PlatzNummer platzNr) {
        if (platzNr == null) {
            throw new IllegalArgumentException("PlatzNr must not be null");
        }
        return platzNr.nummer();
    }
}
