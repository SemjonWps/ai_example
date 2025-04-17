import {Component, Input} from '@angular/core';
import {Platz, Saalplan, SitzplatzStatus} from '../../dtos/kartenverkauf';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-saalplan',
  imports: [
    NgForOf
  ],
  templateUrl: './saalplan.component.html',
  styleUrl: './saalplan.component.css'
})
export class SaalplanComponent {

  @Input()
  saalplanDto: Saalplan | undefined;

  @Input()
  angebotenePlaetze: Platz[] | undefined

  radius = 20;
  spacing = 53;
  rowSpacing = 80;
  startY = 70;

  get saalplanBreite(): number {
    const maxLength = Math.max(...this.saalplanDto!.platzbelegungen.map(reihe => reihe.length));
    return this.spacing * maxLength + this.radius * 2;
  }

  get saalplanHoehe(): number {
    return this.startY + (this.saalplanDto!.platzbelegungen.length) * this.rowSpacing;
  }

  get leinwandBreite() {
    return this.saalplanBreite - ((this.saalplanBreite / 8) * 2);
  }

  get leinwandPosition() {
    return this.saalplanBreite / 8;
  }

  get platzbelegungenReihe(): Platz[][] {
    return this.saalplanDto?.platzbelegungen ?? [];
  }

  getStartX(row: Platz[]): number {
    const rowWidth = (row.length) * this.spacing;
    return (this.saalplanBreite - rowWidth);
  }

  getFarbeFuerPlatzbelegung(platz: Platz): String {
    const sitzplatzStatus: SitzplatzStatus = this.angebotenePlaetze?.some(angebotenerPlatz => angebotenerPlatz.platznummer === platz.platznummer && angebotenerPlatz.reihennummer === platz.reihennummer) ? SitzplatzStatus.ANGEBOTEN : platz.sitzplatzStatus
    switch (sitzplatzStatus) {
      case 'FREI':
        return 'white';
      case 'BELEGT':
        return 'lightgray';
      case 'ANGEBOTEN':
        return 'lightgreen';
      default:
        return 'gray';
    }
  }
}
