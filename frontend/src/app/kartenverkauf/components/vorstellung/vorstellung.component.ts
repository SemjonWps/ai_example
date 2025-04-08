import {Component, Input} from '@angular/core';
import {VorstellungDto} from '../../dtos/kartenverkauf';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-vorstellung',
  imports: [
    DatePipe
  ],
  templateUrl: './vorstellung.component.html',
  styleUrl: './vorstellung.component.css',
  standalone: true,
})
export class VorstellungComponent {

  @Input()
  vorstellung: VorstellungDto | undefined;

}
