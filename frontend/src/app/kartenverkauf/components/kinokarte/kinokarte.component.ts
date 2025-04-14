import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {AngebotDto, VorstellungDto} from '../../dtos/kartenverkauf';
import {DatePipe} from '@angular/common';
import {isPresent} from '../../../common/utils';

@Component({
  selector: 'app-kinokarte',
  imports: [
    DatePipe
  ],
  templateUrl: './kinokarte.component.html',
  styleUrl: './kinokarte.component.css'
})
export class KinokarteComponent implements OnChanges {

  @Input()
  angebot: AngebotDto | undefined;
  @Input()
  vorstellung: VorstellungDto | undefined;

  plaetze: string = "";

  preisInEuro: string = "";

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['angebot'] && isPresent(this.angebot?.platzDtos.at(0))) {
      this.plaetze = "Reihe " + this.angebot.platzDtos.at(0)!.reihe.reihennummer + ", Platz " + this.angebot.platzDtos
        .map(platz => platz.sitz.platznummer.toString())
        .reduce((previousValue: string, currentValue: string) => previousValue === "" ? currentValue : previousValue + ", " + currentValue)

      this.preisInEuro = this.angebot?.gesamtpreis.betragInEuroCent.toString();
      this.preisInEuro = this.preisInEuro.substring(0, this.preisInEuro.length - 2) + "," + this.preisInEuro.substring(this.preisInEuro.length - 2)
    }
  }
}
