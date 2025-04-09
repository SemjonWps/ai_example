import {Component, Input} from '@angular/core';
import {AngebotDto} from '../../dtos/kartenverkauf';

@Component({
  selector: 'app-saalplan',
  imports: [],
  templateUrl: './saalplan.component.html',
  styleUrl: './saalplan.component.css'
})
export class SaalplanComponent {

  @Input()
  angebot: AngebotDto | undefined;

}
