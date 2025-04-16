package de.wps.ddd.kino.kartenverkauf.api.mappers;

import de.wps.ddd.kino.kartenverkauf.api.model.PlatzDto;
import de.wps.ddd.kino.kartenverkauf.api.model.SaalplanDto;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Platz;
import de.wps.ddd.kino.kartenverkauf.domain.entities.Saalplan;
import de.wps.ddd.kino.kartenverkauf.domain.enums.SitzplatzStatus;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Platznummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reihennummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Reservierungsnummer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SaalplanDtoMapperTest {

    SaalplanDtoMapper mapper = new SaalplanDtoMapper();

    @Test
    void saalplantoSaalplanDto() {
        // arrange
        List<Platz> platzListe = new ArrayList<>();

        Reihennummer reihennummer1 = new Reihennummer(1);
        Reihennummer reihennummer2 = new Reihennummer(2);

        Platznummer platznummer1 = new Platznummer(1);
        Platznummer platznummer2 = new Platznummer(2);
        Platznummer platznummer3 = new Platznummer(3);


        platzListe.add(new Platz(null, platznummer1, reihennummer1, false, null, null));
        platzListe.add(new Platz(null, platznummer2, reihennummer1, true, null, null));
        platzListe.add(new Platz(null, platznummer3, reihennummer1, true, null, null));
        platzListe.add(new Platz(null, platznummer1, reihennummer2, false, null, null));
        platzListe.add(new Platz(null, platznummer2, reihennummer2, false, null, null));
        platzListe.add(new Platz(null, platznummer3, reihennummer2, false, new Reservierungsnummer("reservierungsnummer"), null));


        Saalplan saalplan = new Saalplan(null, null, platzListe);
        PlatzDto[][] expectedPlatzDtos = {
                {new PlatzDto(1, 1, SitzplatzStatus.FREI), new PlatzDto(1, 2, SitzplatzStatus.BELEGT), new PlatzDto(1, 3, SitzplatzStatus.BELEGT)},
                {new PlatzDto(2, 1, SitzplatzStatus.FREI), new PlatzDto(2, 2, SitzplatzStatus.FREI), new PlatzDto(2, 3, SitzplatzStatus.FREI)},
                {new PlatzDto(3, 1, SitzplatzStatus.FREI), new PlatzDto(3, 2, SitzplatzStatus.FREI), new PlatzDto(3, 3, SitzplatzStatus.BELEGT)},
        };


        // act
        SaalplanDto saalplanDto = mapper.saalplantoSaalplanDto(saalplan);

        // assert
        assertThat(saalplanDto.platzbelegungen()).isEqualTo(expectedPlatzDtos);
    }
}