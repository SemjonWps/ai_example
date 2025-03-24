import {Component, OnInit} from '@angular/core';
import {ProgrammService} from '../../services/programm.service';
import {Programm} from '../../dtos/programm';
import {KalenderComponent} from '../kalender/kalender.component';
import {ProgrammeintragComponent} from '../programmeintrag/programmeintrag.component';
import {NgForOf} from '@angular/common';
import {format} from 'date-fns';

@Component({
  selector: 'app-programm',
  imports: [
    KalenderComponent,
    ProgrammeintragComponent,
    NgForOf
  ],
  templateUrl: './programm.component.html',
  styleUrl: './programm.component.css'
})
export class ProgrammComponent implements OnInit {

  gewaehltesDatum: Date = new Date("2025-03-19");

  programm?: Programm;

  constructor(private programmService: ProgrammService) {
  }

  ngOnInit() {
    this.programmService.holeProgramm(format(this.gewaehltesDatum, "yyyy-MM-dd")).subscribe(
      data => {
        this.programm = data;
      }
    )
  }

  waehleDatum(datum: Date) {
    this.gewaehltesDatum = datum;
  }
}
