import {Component, Input} from '@angular/core';
import {Film} from '../../dtos/programm';
import {NgIf} from '@angular/common';
import {DurationPipe} from '../../services/filmlaufzeit.pipe';

@Component({
  selector: 'app-film',
  imports: [
    NgIf,
    DurationPipe
  ],
  templateUrl: './film.component.html',
  styleUrl: './film.component.css'
})

export class FilmComponent {

  @Input()
  film!: Film | undefined;

}
