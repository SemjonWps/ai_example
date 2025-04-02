package de.wps.dddschulung.kartenverkauf.persistence.mapper;

import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import de.wps.dddschulung.kartenverkauf.persistence.model.PlatzEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface PlatzMapper {
    @Mapping(target = "reservierungsnummer", source = "reservierungsnummer", qualifiedByName = "StringToReservierungsnummer")
    @Mapping(target = "reihe", source = "reihennummer", qualifiedByName = "ReihennummerToReihe")
    @Mapping(target = "sitz", source = "platznummer", qualifiedByName = "PlatznummerToSitz")
    Platz platzEntityToPlatz(PlatzEntity platzEntity);

    @Mapping(target = "reservierungsnummer", source = "reservierungsnummer", qualifiedByName = "ReservierungsnummerToString")
    @Mapping(target = "reihennummer", source = "reihe", qualifiedByName = "ReiheToReihennummer")
    @Mapping(target = "platznummer", source = "sitz", qualifiedByName = "SitzToPlatznummer")
    PlatzEntity platzToPlatzEntity(Platz platz);

    @Named("ReservierungsnummerToString")
    default String mapReservierungsnummerToString(Reservierungsnummer reservierungsnummer) {
        return reservierungsnummer == null ? null : reservierungsnummer.reservierungsnummer();
    }

    @Named("StringToReservierungsnummer")
    default Reservierungsnummer mapStringToReservierungsnummer(String reservierungsnummer) {
        return reservierungsnummer == null ? null : new Reservierungsnummer(reservierungsnummer);
    }

    @Named("ReihennummerToReihe")
    default Reihe mapReihennummerToReihe(int reihennummer) {
        return new Reihe(reihennummer);
    }

    @Named("ReiheToReihennummer")
    default int mapReiheToReihennummer(Reihe reihe) {
        if (reihe == null) {
            throw new IllegalArgumentException("Reihe must not be null");
        }
        return reihe.reihennummer();
    }

    @Named("PlatznummerToSitz")
    default Sitz mapPlatznummerToSitz(int platznummer) {
        return new Sitz(platznummer);
    }

    @Named("SitzToPlatznummer")
    default int mapSitzToPlatznummer(Sitz sitz) {
        if (sitz == null) {
            throw new IllegalArgumentException("Sitz must not be null");
        }
        return sitz.platznummer();
    }
}
