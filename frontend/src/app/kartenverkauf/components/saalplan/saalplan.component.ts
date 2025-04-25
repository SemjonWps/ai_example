import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Platz, Saalplan, Vorstellung, ZusammenhaengendePlaetze} from '../../dtos/kartenverkauf';
import {NgForOf} from '@angular/common';
import {KartenverkaufService} from '../../services/kartenverkauf.service';

@Component({
  selector: 'app-saalplan',
  imports: [
    NgForOf
  ],
  templateUrl: './saalplan.component.html',
  styleUrl: './saalplan.component.css'
})
export class SaalplanComponent implements OnInit {

  @Input({required: true})
  vorstellung!: Vorstellung;

  @Input({required: true})
  platzanzahl!: number;

  @Output()
  onPlatzwahlBestaetigt = new EventEmitter<ZusammenhaengendePlaetze>();

  saalplan: Saalplan | undefined;
  angebotenePlaetze: ZusammenhaengendePlaetze | undefined;
  gewaehltePlaetze: ZusammenhaengendePlaetze | undefined;

  radius = 20;
  spacing = 53;
  rowSpacing = 80;
  startY = 70;

  constructor(private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.kartenverkaufService.holeSaalplan(this.vorstellung!.uuid).subscribe(
      (data: Saalplan) => {
        console.log(data);
        this.saalplan = data;
      }
    )
    this.kartenverkaufService.sucheZusammenhaengendePlaetze(this.vorstellung.uuid, this.platzanzahl).subscribe(
      (data: ZusammenhaengendePlaetze) => {
        this.angebotenePlaetze = data;
        this.gewaehltePlaetze = data;
      }
    )
  }

  platzwahlBestaetigt() {
    this.onPlatzwahlBestaetigt.emit(this.gewaehltePlaetze!);
  }

  get saalplanBreite(): number {
    const maxLength = Math.max(...this.saalplan!.plaetze.map(reihe => reihe.length));
    return this.spacing * maxLength + this.radius * 2;
  }

  get saalplanHoehe(): number {
    return this.startY + (this.saalplan!.plaetze.length) * this.rowSpacing;
  }

  get leinwandBreite() {
    return this.saalplanBreite - ((this.saalplanBreite / 8) * 2);
  }

  get leinwandPosition() {
    return this.saalplanBreite / 8;
  }

  get platzbelegungenReihe(): Platz[][] {
    return this.saalplan?.plaetze ?? [];
  }

  getStartX(row: Platz[]): number {
    const rowWidth = (row.length) * this.spacing;
    return (this.saalplanBreite - rowWidth);
  }

  getFarbeFuerPlatzbelegung(platz: Platz): String {
    if (this.angebotenePlaetze?.plaetze.some(angebotenerPlatz => angebotenerPlatz.platzNr === platz.platzNr && angebotenerPlatz.reiheNr === platz.reiheNr)) {
      return 'lightgreen';
    }

    if (platz.istFrei) {
      return 'white';
    }

    return 'lightgray';
  }
}
