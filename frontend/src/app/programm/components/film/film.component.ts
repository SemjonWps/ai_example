import {Component, Input} from '@angular/core';
import {Film} from '../../dtos/programm';

@Component({
  selector: 'app-film',
  imports: [],
  templateUrl: './film.component.html',
  styleUrl: './film.component.css'
})
export class FilmComponent {

  @Input()
  film!: Film | undefined;

}
