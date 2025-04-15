import {Component, Input} from '@angular/core';
import {AngebotDto, VorstellungDto} from '../../dtos/kartenverkauf';
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

  @Input()
  angebot: AngebotDto | undefined;
  @Input()
  vorstellung: VorstellungDto | undefined;


  createPlaetzeString(): string {
    if (isPresent(this.angebot?.platzDtos.at(0))) {
      return "Reihe " + this.angebot.platzDtos.at(0)!.reihe.reihennummer + ", Platz " + this.angebot.platzDtos
        .map(platz => platz.sitz.platznummer.toString())
        .reduce((previousValue: string, currentValue: string) => previousValue === "" ? currentValue : previousValue + ", " + currentValue)
    }
    return "";
  }
}
