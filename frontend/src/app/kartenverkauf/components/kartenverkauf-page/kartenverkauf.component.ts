import {Component, OnInit} from '@angular/core';
import {VorstellungComponent} from '../vorstellung/vorstellung.component';
import {Vorstellung} from '../../dtos/kartenverkauf';

@Component({
  selector: 'app-kartenverkauf',
  imports: [
    VorstellungComponent
  ],
  templateUrl: './kartenverkauf.component.html',
  styleUrl: './kartenverkauf.component.css'
})
export class KartenverkaufComponent implements OnInit {

  vorstellung: Vorstellung | undefined;

  ngOnInit(): void {
    this.vorstellung = {
      anfangszeit: '2025-04-01 20:00:00',
      saal: 'Großer Saal',
      filmname: 'Back to the Futura'
    }
  }

}
