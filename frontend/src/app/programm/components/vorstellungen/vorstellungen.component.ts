import {Component, Input} from '@angular/core';
import {Vorstellung} from '../../dtos/programm';
import {DatePipe, NgForOf} from "@angular/common";
import {Router} from '@angular/router';

@Component({
  selector: 'app-vorstellungen',
  imports: [
    DatePipe,
    NgForOf
  ],
  templateUrl: './vorstellungen.component.html',
  styleUrl: './vorstellungen.component.css',
  standalone: true,
})
export class VorstellungenComponent {

  @Input()
  vorstellungen!: Vorstellung[] | undefined;

  constructor(private router: Router) {
  }

  navigateToKartenverkauf(vorstellungUuid: string) {
    this.router.navigate(['/kartenverkauf', vorstellungUuid]);
  }
}
