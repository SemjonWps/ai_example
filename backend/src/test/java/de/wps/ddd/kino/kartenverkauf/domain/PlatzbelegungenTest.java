package de.wps.ddd.kino.kartenverkauf.domain;

import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Sitz;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlatzbelegungenTest {

    private Map<Reihe, List<Platz>> erstellePlatzbelegungenMap() {
        Map<Reihe, List<Platz>> platzbelegungenMap = new HashMap<>();
        Reservierungsnummer reservierungsnummer = new Reservierungsnummer("reservierungsnummer");
        Reihe reihe1 = new Reihe(1);
        Reihe reihe2 = new Reihe(2);
        Platz platz1Reihe1 = new Platz(11L, new Sitz(1), new Reihe(1), false, null, 5L);
        Platz platz2Reihe1 = new Platz(12L, new Sitz(2), new Reihe(1), false, reservierungsnummer, 5L);
        Platz platz3Reihe1 = new Platz(13L, new Sitz(3), new Reihe(1), false, null, 5L);
        Platz platz1Reihe2 = new Platz(21L, new Sitz(1), new Reihe(2), true, null, 5L);
        Platz platz2Reihe2 = new Platz(22L, new Sitz(2), new Reihe(2), false, null, 5L);
        Platz platz3Reihe2 = new Platz(23L, new Sitz(3), new Reihe(2), false, null, 5L);

        List<Platz> platzListeReihe1 = new ArrayList<>();
        platzListeReihe1.add(platz1Reihe1);
        platzListeReihe1.add(platz2Reihe1);
        platzListeReihe1.add(platz3Reihe1);
        List<Platz> platzListeReihe2 = new ArrayList<>();
        platzListeReihe2.add(platz2Reihe2);
        platzListeReihe2.add(platz1Reihe2);
        platzListeReihe2.add(platz3Reihe2);

        platzbelegungenMap.put(reihe1, platzListeReihe1);
        platzbelegungenMap.put(reihe2, platzListeReihe2);

        return platzbelegungenMap;
    }

    @Test
    public void testKonstruktor() {
        // arrange and act
        Platzbelegungen platzbelegungen = new Platzbelegungen(erstellePlatzbelegungenMap());

        // assert
        assertThat(platzbelegungen.getPlatzbelegungen()[0][0]).isEqualTo(SitzplatzStatus.FREI);
        assertThat(platzbelegungen.getPlatzbelegungen()[0][1]).isEqualTo(SitzplatzStatus.BELEGT);
        assertThat(platzbelegungen.getPlatzbelegungen()[0][2]).isEqualTo(SitzplatzStatus.FREI);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][0]).isEqualTo(SitzplatzStatus.BELEGT);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][1]).isEqualTo(SitzplatzStatus.FREI);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][2]).isEqualTo(SitzplatzStatus.FREI);
    }

    @Test
    public void testKonstruktor_plaetzeParameterLeer_wirftException() {
        // arrange
        Map<Reihe, List<Platz>> platzbelegungenMap = new HashMap<>();

        // act and assert
        assertThatThrownBy(() -> new Platzbelegungen(platzbelegungenMap))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Keine Plätze in Saalplan vorhanden.");
    }

    @Test
    public void markiereAlsAngeboten_korrekterInput_plaetzeWerdenMarkiert() {
        // arrange
        Platzbelegungen platzbelegungen = new Platzbelegungen(erstellePlatzbelegungenMap());

        Platz platz2Reihe2 = new Platz(22L, new Sitz(2), new Reihe(2), false, null, 5L);
        Platz platz3Reihe2 = new Platz(23L, new Sitz(3), new Reihe(2), false, null, 5L);
        List<Platz> zusammenhaengendePlaetzeListe = new ArrayList<>();
        zusammenhaengendePlaetzeListe.add(platz2Reihe2);
        zusammenhaengendePlaetzeListe.add(platz3Reihe2);

        ZusammenhaengendePlaetze zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(zusammenhaengendePlaetzeListe);

        // act
        platzbelegungen.markiereAlsAngeboten(zusammenhaengendePlaetze);

        // assert
        assertThat(platzbelegungen.getPlatzbelegungen()[0][0]).isEqualTo(SitzplatzStatus.FREI);
        assertThat(platzbelegungen.getPlatzbelegungen()[0][1]).isEqualTo(SitzplatzStatus.BELEGT);
        assertThat(platzbelegungen.getPlatzbelegungen()[0][2]).isEqualTo(SitzplatzStatus.FREI);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][0]).isEqualTo(SitzplatzStatus.BELEGT);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][1]).isEqualTo(SitzplatzStatus.ANGEBOTEN);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][2]).isEqualTo(SitzplatzStatus.ANGEBOTEN);
    }

    @Test
    public void markiereAlsAngeboten_leererInput_keinePlaetzeWerdenMarkiert() {
        // arrange
        Platzbelegungen platzbelegungen = new Platzbelegungen(erstellePlatzbelegungenMap());
        List<Platz> zusammenhaengendePlaetzeListe = new ArrayList<>();
        ZusammenhaengendePlaetze zusammenhaengendePlaetze = new ZusammenhaengendePlaetze(zusammenhaengendePlaetzeListe);

        // act
        platzbelegungen.markiereAlsAngeboten(zusammenhaengendePlaetze);

        // assert
        assertThat(platzbelegungen.getPlatzbelegungen()[0][0]).isEqualTo(SitzplatzStatus.FREI);
        assertThat(platzbelegungen.getPlatzbelegungen()[0][1]).isEqualTo(SitzplatzStatus.BELEGT);
        assertThat(platzbelegungen.getPlatzbelegungen()[0][2]).isEqualTo(SitzplatzStatus.FREI);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][0]).isEqualTo(SitzplatzStatus.BELEGT);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][1]).isEqualTo(SitzplatzStatus.FREI);
        assertThat(platzbelegungen.getPlatzbelegungen()[1][2]).isEqualTo(SitzplatzStatus.FREI);
    }

}
