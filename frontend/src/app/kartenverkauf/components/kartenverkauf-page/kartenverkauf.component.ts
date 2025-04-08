import {Component, OnInit} from '@angular/core';
import {VorstellungComponent} from '../vorstellung/vorstellung.component';
import {VorstellungDto} from '../../dtos/kartenverkauf';
import {PlatzanzahlComponent} from '../platzanzahl/platzanzahl.component';
import {KartenverkaufService} from '../../services/kartenverkauf.service';
import {isPresent} from '../../../common/utils';

@Component({
  selector: 'app-kartenverkauf',
  imports: [
    VorstellungComponent,
    PlatzanzahlComponent
  ],
  templateUrl: './kartenverkauf.component.html',
  styleUrl: './kartenverkauf.component.css',
  standalone: true,
})
export class KartenverkaufComponent implements OnInit {

  vorstellung: VorstellungDto | undefined;

  constructor(private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.vorstellung = {
      anfangszeit: '2025-04-01 20:00:00',
      saal: 'Großer Saal',
      filmname: 'Back to the Futura',
      uuid: 'a095c8f6-6fa2-4f2e-acf1-52cee0698e74',
    }
  }

  forderePlaetzeAn($event: number) {
    if (!isPresent(this.vorstellung)) {
      return;
    }
    this.kartenverkaufService.holeZusammenhaengendePlaetze($event, this.vorstellung.uuid)
  }
}
