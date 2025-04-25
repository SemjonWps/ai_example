import {Component, OnInit} from '@angular/core';
import {VorstellungComponent} from '../vorstellung/vorstellung.component';
import {Kinokarte, Vorstellung, Zahlungsbestaetigung, ZusammenhaengendePlaetze} from '../../dtos/kartenverkauf';
import {PlatzanzahlComponent} from '../platzanzahl/platzanzahl.component';
import {SaalplanComponent} from '../saalplan/saalplan.component';
import {ZahlungComponent} from '../zahlung/zahlung.component';
import {KinokarteComponent} from '../kinokarte/kinokarte.component';
import {ActivatedRoute} from '@angular/router';
import {NavbarComponent} from '../../../common/components/navbar/navbar.component';

@Component({
  selector: 'app-kartenverkauf',
  imports: [
    VorstellungComponent,
    PlatzanzahlComponent,
    SaalplanComponent,
    ZahlungComponent,
    KinokarteComponent,
    NavbarComponent
  ],
  templateUrl: './kartenverkauf.component.html',
  styleUrl: './kartenverkauf.component.css',
  standalone: true,
})
export class KartenverkaufComponent implements OnInit {

  vorstellungUuid: string | undefined;
  gewaehlteVorstellung: Vorstellung | undefined;
  gewaehltePlatzanzahl: number | undefined;
  gewaehltePlaetze: ZusammenhaengendePlaetze | undefined;
  zahlungsbestaetigung: Zahlungsbestaetigung | undefined;
  erhalteneKinokarten: Kinokarte[] | undefined;

  constructor(
    private activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    const uuid: string | null = this.activatedRoute.snapshot.paramMap.get('vorstellungUuid');
    this.vorstellungUuid = uuid ?? undefined;
    console.log(uuid, this.vorstellungUuid);
  }

  get zeigeVorstellungAktiv(): boolean {
    console.log(this.vorstellungUuid, this.vorstellungUuid !== undefined);
    return this.vorstellungUuid !== undefined;
  }

  get zeigeVorstellungFertig(): boolean {
    return this.gewaehlteVorstellung !== undefined;
  }

  get zeigePlatzanzahlAktiv(): boolean {
    return this.gewaehlteVorstellung !== undefined && this.gewaehltePlatzanzahl === undefined;
  }

  get zeigePlatzanzahlFertig(): boolean {
    return this.gewaehltePlatzanzahl !== undefined;
  }

  get zeigePlatzwahlAktiv(): boolean {
    return this.gewaehltePlatzanzahl !== undefined && this.gewaehltePlaetze === undefined;
  }

  get zeigePlatzwahlFertig(): boolean {
    return this.gewaehltePlaetze !== undefined;
  }

  get zeigeZahlungAktiv(): boolean {
    return this.gewaehltePlaetze !== undefined && this.zahlungsbestaetigung === undefined;
  }

  get zeigeZahlungFertig(): boolean {
    return this.zahlungsbestaetigung !== undefined;
  }

  get zeigeKinokartenAktiv(): boolean {
    return this.zahlungsbestaetigung !== undefined && this.erhalteneKinokarten === undefined;
  }

  get zeigeKinokartenFertig(): boolean {
    return this.erhalteneKinokarten !== undefined;
  }


  vorstellungGeladen(vorstellung: Vorstellung) {
    this.gewaehlteVorstellung = vorstellung;
  }

  platzanzahlGewaehlt(platzanzahl: number) {
    this.gewaehltePlatzanzahl = platzanzahl;
  }

  plaetzeGewaehlt(plaetze: ZusammenhaengendePlaetze) {
    this.gewaehltePlaetze = plaetze;
  }

  zahlungBestaetigt(zahlungsbestaetigung: Zahlungsbestaetigung) {
    this.zahlungsbestaetigung = zahlungsbestaetigung;
  }

  kinokartenGedruckt(kinokarten: Kinokarte[]) {
    this.erhalteneKinokarten = kinokarten;
  }

}
