import {Component, Input} from '@angular/core';
import {Vorstellung} from '../../dtos/programm';

@Component({
  selector: 'app-vorstellungen',
  imports: [],
  templateUrl: './vorstellungen.component.html',
  styleUrl: './vorstellungen.component.css'
})
export class VorstellungenComponent {

  @Input()
  vorstellungen!: Vorstellung[] | undefined;

  constructor() {
  }
}
