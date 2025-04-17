package de.wps.ddd.kino.kartenverkauf.persistence.mappers;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.persistence.model.PlatzEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PlatzMapper {
    @Mapping(target = "reservierungsnummer", source = "reservierungsnummer", qualifiedByName = "StringToReservierungsnummer")
    @Mapping(target = "platzId.reihennummer", source = "reihennummer", qualifiedByName = "IntToReihennummer")
    @Mapping(target = "platzId.platznummer", source = "platznummer", qualifiedByName = "IntToPlatznummer")
    Platz platzEntityToPlatz(PlatzEntity platzEntity);

    @Mapping(target = "reservierungsnummer", source = "reservierungsnummer", qualifiedByName = "ReservierungsnummerToString")
    @Mapping(target = "reihennummer", source = "platzId.reihennummer", qualifiedByName = "ReihennummerToInt")
    @Mapping(target = "platznummer", source = "platzId.platznummer", qualifiedByName = "PlatznummerToInt")
    PlatzEntity platzToPlatzEntity(Platz platz);

    @Named("ReservierungsnummerToString")
    default String mapReservierungsnummerToString(Reservierungsnummer reservierungsnummer) {
        return reservierungsnummer == null ? null : reservierungsnummer.reservierungsnummer();
    }

    @Named("StringToReservierungsnummer")
    default Reservierungsnummer mapStringToReservierungsnummer(String reservierungsnummer) {
        return reservierungsnummer == null ? null : new Reservierungsnummer(reservierungsnummer);
    }

    @Named("IntToReihennummer")
    default Reihennummer mapReihennummerToReihe(int reihennummer) {
        return new Reihennummer(reihennummer);
    }

    @Named("ReihennummerToInt")
    default int mapReiheToReihennummer(Reihennummer reihennummer) {
        if (reihennummer == null) {
            throw new IllegalArgumentException("Reihennummer must not be null");
        }
        return reihennummer.nummer();
    }

    @Named("IntToPlatznummer")
    default Platznummer mapPlatznummerToSitz(int platznummer) {
        return new Platznummer(platznummer);
    }

    @Named("PlatznummerToInt")
    default int mapSitzToPlatznummer(Platznummer platznummer) {
        if (platznummer == null) {
            throw new IllegalArgumentException("Platznummer must not be null");
        }
        return platznummer.nummer();
    }
}
