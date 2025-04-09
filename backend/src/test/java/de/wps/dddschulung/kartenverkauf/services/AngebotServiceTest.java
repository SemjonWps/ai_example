package de.wps.dddschulung.kartenverkauf.services;

import de.wps.dddschulung.kartenverkauf.api.model.PlatzDto;
import de.wps.dddschulung.kartenverkauf.domain.Angebot;
import de.wps.dddschulung.kartenverkauf.domain.Platzbelegungen;
import de.wps.dddschulung.kartenverkauf.domain.ZusammenhaengendePlaetze;
import de.wps.dddschulung.kartenverkauf.domain.entities.Platz;
import de.wps.dddschulung.kartenverkauf.domain.entities.Saalplan;
import de.wps.dddschulung.kartenverkauf.domain.repositories.SaalplanStapel;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Geldbetrag;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Reihe;
import de.wps.dddschulung.kartenverkauf.domain.valueobjects.Sitz;
import de.wps.dddschulung.kartenverkauf.persistence.repositories.VorstellungRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AngebotServiceTest {
    @Mock
    SaalplanStapel saalplanStapel;
    @Mock
    VorstellungRepository vorstellungRepository;
    @Mock
    Saalplan saalplan;
    @Mock
    ZusammenhaengendePlaetze zusammenhaengendePlaetze;
    @Mock
    Platzbelegungen platzbelegungen;
    @InjectMocks
    AngebotService angebotService;
    String uuidString = "95b21a30-64bf-4df1-a0a2-e769bd7c5ea1";
    UUID vorstellungUUID = UUID.fromString(uuidString);

    @Test
    void holeAngebot_nichtGenugZusammenhaengendePlaetzeVorhanden_returnsAngebotFuerKeinePlaetze() {
        // arrange
        int platzanzahl = 2;
        Mockito.when(saalplanStapel.holeSaalplan(vorstellungUUID)).thenReturn(saalplan);
        Mockito.when(saalplan.sucheZusammenhaengendePlaetze(platzanzahl)).thenReturn(zusammenhaengendePlaetze);
        Mockito.when(zusammenhaengendePlaetze.plaetze()).thenReturn(List.of());
        Mockito.when(saalplan.holePlatzbelegungen(zusammenhaengendePlaetze)).thenReturn(platzbelegungen);

        // act
        Angebot angebot = angebotService.holeAngebot(platzanzahl, uuidString);

        // assert
        assertThat(angebot).isNotNull();
        assertThat(angebot.getPlatzbelegungen()).isEqualTo(platzbelegungen);
        assertThat(angebot.getPlatzDtos()).isEqualTo(null);
        assertThat(angebot.getGesamtpreis()).isEqualTo(new Geldbetrag(0));
    }

    @Test
    void holeAngebot_zusammenhaengendePlaetzeVorhanden_returnsAngebotZusammenhaengendePlaetze() {
        // arrange
        int platzanzahl = 2;
        Mockito.when(saalplanStapel.holeSaalplan(vorstellungUUID)).thenReturn(saalplan);
        Mockito.when(saalplan.sucheZusammenhaengendePlaetze(platzanzahl)).thenReturn(zusammenhaengendePlaetze);
        Reihe reihe = new Reihe(1);
        Platz platz1 = new Platz(1L, new Sitz(11), reihe, false, null, 5L);
        Platz platz2 = new Platz(2L, new Sitz(12), reihe, false, null, 5L);
        Mockito.when(zusammenhaengendePlaetze.plaetze()).thenReturn(List.of(platz1, platz2));
        Mockito.when(vorstellungRepository.findEintrittspreisByUuid(vorstellungUUID)).thenReturn(750);
        Mockito.when(saalplan.holePlatzbelegungen(zusammenhaengendePlaetze)).thenReturn(platzbelegungen);

        // act
        Angebot angebot = angebotService.holeAngebot(platzanzahl, uuidString);

        // assert
        assertThat(angebot).isNotNull();
        assertThat(angebot.getPlatzbelegungen()).isEqualTo(platzbelegungen);
        assertThat(angebot.getPlatzDtos()).containsExactly(new PlatzDto(new Reihe(1), new Sitz(11)), new PlatzDto(new Reihe(1), new Sitz(12)));
        assertThat(angebot.getGesamtpreis()).isEqualTo(new Geldbetrag(1500));
    }
}