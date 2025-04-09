import {Component, Input} from '@angular/core';
import {Platzbelegungen} from '../../dtos/kartenverkauf';

@Component({
  selector: 'app-saalplan',
  imports: [],
  templateUrl: './saalplan.component.html',
  styleUrl: './saalplan.component.css'
})
export class SaalplanComponent {

  @Input()
  platzbelegungen: Platzbelegungen | undefined;

}
