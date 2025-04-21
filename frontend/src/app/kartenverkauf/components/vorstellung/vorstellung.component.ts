import {Component, Input} from '@angular/core';
import {Vorstellung} from '../../dtos/kartenverkauf';
import {DatePipe} from '@angular/common';
import {Router} from '@angular/router';

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
  vorstellung: Vorstellung | undefined;

  constructor(private router: Router) {
  }

  zurueckZumProgramm() {
    this.router.navigate(['/programm']);
  }

}
