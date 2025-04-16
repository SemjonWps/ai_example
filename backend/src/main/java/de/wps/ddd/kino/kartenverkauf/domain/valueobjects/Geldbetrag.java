package de.wps.ddd.kino.kartenverkauf.domain.valueobjects;

import lombok.Value;
import org.springframework.util.Assert;

@Value(staticConstructor = "of")
public class Geldbetrag {

    Betrag betrag;
    Waehrung waehrung;

    public static Geldbetrag euroInCent(int cent) {
        return Geldbetrag.of(Betrag.of(cent), Waehrung.EUR);
    }

    public static Geldbetrag euro(int euro, int cent) {
        return Geldbetrag.of(Betrag.of(euro * 100 + cent), Waehrung.EUR);
    }

    public Geldbetrag plus(Geldbetrag other) {
        Assert.isTrue(this.waehrung.equals(other.waehrung), "Währungen müssen übereinstimmen");
        return Geldbetrag.of(this.betrag.plus(other.betrag), this.waehrung);
    }

    public Geldbetrag mal(int anzahl) {
        return Geldbetrag.of(betrag.mal(anzahl), waehrung);
    }

    @Value(staticConstructor = "of")
    public static class Betrag {

        int betrag;

        private Betrag(int betrag) {
            Assert.isTrue(betrag >= 0, "Betrag muss größer gleich 0 sein.");
            this.betrag = betrag;
        }

        public Betrag plus(Betrag other) {
            return new Betrag(this.betrag + other.betrag);
        }

        public Betrag mal(int anzahl) {
            return new Betrag(this.betrag * anzahl);
        }
    }

    public enum Waehrung {
        EUR
    }
}