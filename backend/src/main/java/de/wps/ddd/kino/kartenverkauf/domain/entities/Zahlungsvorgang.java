package de.wps.ddd.kino.kartenverkauf.domain.entities;

import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Auftragsnummer;
import de.wps.ddd.kino.kartenverkauf.domain.valueobjects.Zahlungsstatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@AllArgsConstructor
public class Zahlungsvorgang {

    private final Auftragsnummer auftragsnummer;
    private Zahlungsstatus status;

    public static Zahlungsvorgang starteZahlung() {
        return new Zahlungsvorgang(Auftragsnummer.neueAuftragsnummer(), Zahlungsstatus.Ausstehend);
    }

    public void zahlungEingegangen() {
        Assert.isTrue(this.status == Zahlungsstatus.Ausstehend, "Nur ausstehende Zahlungsvorgänge können abgeschlossen werden.");
        this.status = Zahlungsstatus.Eingegangen;
    }

    public void zahlungAbgebrochen() {
        Assert.isTrue(this.status == Zahlungsstatus.Ausstehend, "Nur ausstehende Zahlungsvorgänge können abgebrochen werden.");
        this.status = Zahlungsstatus.Abgebrochen;
    }
}
