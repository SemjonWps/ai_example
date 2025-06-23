import {Component} from '@angular/core';
import {ProgrammService} from '../../services/programm.service';
import {Programm} from '../../dtos/programm';
import {KalenderComponent} from '../kalender/kalender.component';
import {ProgrammeintragComponent} from '../programmeintrag/programmeintrag.component';
import {NgForOf} from '@angular/common';
import {format} from 'date-fns';
import {NavbarComponent} from '../../../common/components/navbar/navbar.component';

@Component({
  selector: 'app-programm',
  imports: [
    KalenderComponent,
    ProgrammeintragComponent,
    NgForOf,
    NavbarComponent
  ],
  templateUrl: './programm.component.html',
  styleUrl: './programm.component.css',
  standalone: true,
})
export class ProgrammComponent {

  programm?: Programm;

  constructor(private programmService: ProgrammService) {
  }

  waehleDatum(datum: Date) {
    this.programmService.holeProgramm(format(datum, "yyyy-MM-dd")).subscribe(
      data => {
        this.programm = data;
      }
    )
  }
}
