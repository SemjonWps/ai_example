package de.wps.dddschulung.kartenverkauf.domain;

public interface SaalplanStapel {

    Saalplan holeSaalplan(Vorstellung vorstellung);

    void legeZurueck(Saalplan saalplan);
}
