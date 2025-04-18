import {Component, Input} from '@angular/core';
import {Angebot, Vorstellung} from '../../dtos/kartenverkauf';
import {DatePipe, NgIf} from '@angular/common';
import {GeldbetragPipe} from '../../services/geldbetrag.pipe';
import {isPresent} from '../../../common/utils';

@Component({
  selector: 'app-kinokarte',
  imports: [
    DatePipe,
    GeldbetragPipe,
    NgIf
  ],
  templateUrl: './kinokarte.component.html',
  styleUrl: './kinokarte.component.css'
})
export class KinokarteComponent {

  @Input({required: true})
  angebot!: Angebot;
  @Input()
  vorstellung: Vorstellung | undefined;


  createPlaetzeString(): string {
    if (isPresent(this.angebot.plaetze.at(0))) {
      return "Reihe " + this.angebot.plaetze.at(0)!.reiheNr + ", Platz " + this.angebot.plaetze
        .map(platz => platz.platzNr.toString())
        .reduce((previousValue: string, currentValue: string) => previousValue === "" ? currentValue : previousValue + ", " + currentValue)
    }
    throw new Error("Angebot enthält keine Plätze");
  }
}
