import {Component, OnInit} from '@angular/core';
import {VorstellungComponent} from '../vorstellung/vorstellung.component';
import {Vorstellung} from '../../dtos/kartenverkauf';
import {PlatzanzahlComponent} from '../platzanzahl/platzanzahl.component';
import {FormControl} from '@angular/forms';

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

  vorstellung: Vorstellung | undefined;
  platzanzahlControl: FormControl<number | null> = new FormControl<number | null>(0);

  ngOnInit(): void {
    this.vorstellung = {
      anfangszeit: '2025-04-01 20:00:00',
      saal: 'Großer Saal',
      filmname: 'Back to the Futura'
    }
  }

}
