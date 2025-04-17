import {Component, Input} from '@angular/core';
import {Angebot, Vorstellung} from '../../dtos/kartenverkauf';
import {DatePipe} from '@angular/common';
import {GeldbetragPipe} from '../../services/geldbetrag.pipe';
import {isPresent} from '../../../common/utils';

@Component({
  selector: 'app-kinokarte',
  imports: [
    DatePipe,
    GeldbetragPipe
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
    if (isPresent(this.angebot.platzDtos.at(0))) {
      return "Reihe " + this.angebot.platzDtos.at(0)!.reihennummer + ", Platz " + this.angebot.platzDtos
        .map(platz => platz.platznummer.toString())
        .reduce((previousValue: string, currentValue: string) => previousValue === "" ? currentValue : previousValue + ", " + currentValue)
    }
    throw new Error("Angebot enthält keine Plätze");
  }
}
