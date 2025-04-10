import {Component, Input} from '@angular/core';
import {Platzbelegungen, SitzplatzStatus} from '../../dtos/kartenverkauf';
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
  platzbelegungen: Platzbelegungen | undefined;

  radius = 20;
  spacing = 53; // horizontal space between circles
  rowSpacing = 80; // vertical space between rows
  startY = 70;

  get saalplanBreite(): number {
    const maxLength = Math.max(...this.platzbelegungen!.platzbelegungen.map(reihe => reihe.length));
    return this.spacing * maxLength + this.radius * 2;
  }

  get saalplanHoehe(): number {
    return this.startY + (this.platzbelegungen!.platzbelegungen.length) * this.rowSpacing;
  }

  get leinwandBreite() {
    return this.saalplanBreite - ((this.saalplanBreite / 8) * 2);
  }

  get leinwandPosition() {
    return this.saalplanBreite / 8;
  }

  get platzbelegungenReihe(): SitzplatzStatus[][] {
    return this.platzbelegungen?.platzbelegungen ?? [];
  }

  getStartX(row: SitzplatzStatus[]): number {
    const rowWidth = (row.length) * this.spacing;
    return (this.saalplanBreite - rowWidth);
  }

  getFarbeFuerPlatzbelegung(sitzplatzStatus: SitzplatzStatus): String {
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
