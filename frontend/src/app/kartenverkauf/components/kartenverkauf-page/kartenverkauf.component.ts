import {Component, OnInit} from '@angular/core';
import {VorstellungComponent} from '../vorstellung/vorstellung.component';
import {Angebot, Kinokarte, Vorstellung} from '../../dtos/kartenverkauf';
import {PlatzanzahlComponent} from '../platzanzahl/platzanzahl.component';
import {KartenverkaufService} from '../../services/kartenverkauf.service';
import {isPresent} from '../../../common/utils';
import {SaalplanComponent} from '../saalplan/saalplan.component';
import {NgIf} from '@angular/common';
import {ZahlungComponent} from '../zahlung/zahlung.component';
import {ZahlungdialogComponent} from '../zahlung/zahlungdialog/zahlungdialog.component';
import {KinokarteComponent} from '../kinokarte/kinokarte.component';
import {ActivatedRoute} from '@angular/router';
import {NavbarComponent} from '../../../common/components/navbar/navbar.component';

@Component({
  selector: 'app-kartenverkauf',
  imports: [
    VorstellungComponent,
    PlatzanzahlComponent,
    SaalplanComponent,
    NgIf,
    ZahlungComponent,
    ZahlungdialogComponent,
    KinokarteComponent,
    NavbarComponent
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

  constructor(
    private kartenverkaufService: KartenverkaufService,
    private activatedRoute: ActivatedRoute,
  ) {
  }

  ngOnInit(): void {
    const uuid: string | null = this.activatedRoute.snapshot.paramMap.get('vorstellungUuid');
    if (isPresent(uuid)) {
      this.kartenverkaufService.holeVorstellung(uuid).subscribe((vorstellung: Vorstellung) => {
        this.vorstellung = vorstellung
      });
    }
  }

  forderePlaetzeAn($event: number) {
    if (!isPresent(this.vorstellung)) {
      return;
    }
    this.kartenverkaufService.holeZusammenhaengendePlaetze($event, this.vorstellung.uuid).subscribe(
      (data: Angebot) => {
        this.angebot = data;
        this.zeigeSaalplanKomponente = this.angebot.saalplan.platzbelegungen !== undefined;
        this.zeigeZahlungKomponente = this.angebot.gesamtpreis !== undefined;
      }
    )
  }

  oeffneZahlungDialog() {
    this.zeigeZahlungDialogKomponente = true;
  }

  schliesseZahlungDialog() {
    this.kartenverkaufService.speichereVerkauftePlaetze(this.angebot!.plaetze, this.vorstellung!.uuid).subscribe(
      (data: Kinokarte[]) => {
        console.log(data);
        this.zeigeZahlungDialogKomponente = false;
        this.zeigeKinokarteKomponente = true;
      }
    )
  }
}
