import {Component, Input} from '@angular/core';
import {Programmeintrag} from '../../dtos/programm';
import {FilmComponent} from '../film/film.component';
import {VorstellungenComponent} from '../vorstellungen/vorstellungen.component';

@Component({
  selector: 'app-programmeintrag',
  imports: [
    FilmComponent,
    VorstellungenComponent
  ],
  templateUrl: './programmeintrag.component.html',
  styleUrl: './programmeintrag.component.css'
})
export class ProgrammeintragComponent {

  @Input({required: true})
  public programmeintrag: Programmeintrag | undefined;

}
