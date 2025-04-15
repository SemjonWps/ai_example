import {Component, OnInit} from '@angular/core';
import {VorstellungComponent} from '../vorstellung/vorstellung.component';
import {Angebot, Vorstellung} from '../../dtos/kartenverkauf';
import {PlatzanzahlComponent} from '../platzanzahl/platzanzahl.component';
import {KartenverkaufService} from '../../services/kartenverkauf.service';
import {isPresent} from '../../../common/utils';
import {SaalplanComponent} from '../saalplan/saalplan.component';
import {NgIf} from '@angular/common';
import {ZahlungComponent} from '../zahlung/zahlung.component';
import {ZahlungdialogComponent} from '../zahlung/zahlungdialog/zahlungdialog.component';
import {KinokarteComponent} from '../kinokarte/kinokarte.component';

@Component({
  selector: 'app-kartenverkauf',
  imports: [
    VorstellungComponent,
    PlatzanzahlComponent,
    SaalplanComponent,
    NgIf,
    ZahlungComponent,
    ZahlungdialogComponent,
    KinokarteComponent
  ],
  templateUrl: './kartenverkauf.component.html',
  styleUrl: './kartenverkauf.component.css',
  standalone: true,
})
export class KartenverkaufComponent implements OnInit {

  vorstellung: Vorstellung | undefined;
  angebot: Angebot | undefined;
  zeigeSaalplanKomponente: boolean = false;
  zeigeZahlungKomponente: boolean = false;
  zeigeZahlungDialogKomponente: boolean = false;
  zeigeKinokarteKomponente: boolean = false;

  constructor(private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.vorstellung = {
      anfangszeit: '2025-04-01 20:00:00',
      saal: 'Großer Saal',
      filmname: 'Back to the Futura',
      uuid: '95b21a30-64bf-4df1-a0a2-e769bd7c5ea1',
    }
  }

  forderePlaetzeAn($event: number) {
    if (!isPresent(this.vorstellung)) {
      return;
    }
    this.kartenverkaufService.holeZusammenhaengendePlaetze($event, this.vorstellung.uuid).subscribe(
      data => {
        this.angebot = data;
        this.zeigeSaalplanKomponente = this.angebot.platzbelegungen.platzbelegungen !== undefined;
        this.zeigeZahlungKomponente = this.angebot.gesamtpreis !== undefined;
      }
    )
  }

  oeffneZahlungDialog() {
    this.zeigeZahlungDialogKomponente = true;
  }

  schliesseZahlungDialog() {
    this.zeigeZahlungDialogKomponente = false;
    this.zeigeKinokarteKomponente = true;
  }
}
