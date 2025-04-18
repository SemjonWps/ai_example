import {Component, Input} from '@angular/core';
import {Programmeintrag} from '../../dtos/programm';
import {DurationPipe} from '../../services/filmlaufzeit.pipe';
import {DatePipe, NgForOf} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-programmeintrag',
  imports: [
    DurationPipe,
    DatePipe,
    NgForOf
  ],
  templateUrl: './programmeintrag.component.html',
  styleUrl: './programmeintrag.component.css',
  standalone: true,
})
export class ProgrammeintragComponent {

  @Input({required: true})
  public programmeintrag: Programmeintrag | undefined;

  constructor(private router: Router) {
  }

  get film() {
    return this.programmeintrag!.film
  }

  get vorstellungen() {
    return this.programmeintrag!.vorstellungen
  }
  
  navigateToKartenverkauf(vorstellungUuid: string) {
    this.router.navigate(['/kartenverkauf', vorstellungUuid]);
  }
}
