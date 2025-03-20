import {Component, OnInit} from '@angular/core';
import {NgForOf} from "@angular/common";
import {WochentagEnum} from '../../dtos/wochentag.enum';
import {VorstellungService} from '../../services/vorstellung.service';
import {DatumService} from '../../services/datum.service';
import {Film} from '../../dtos/film';

@Component({
  selector: 'app-programm',
  imports: [
    NgForOf
  ],
  templateUrl: './programm.component.html',
  styleUrl: './programm.component.css'
})
export class ProgrammComponent implements OnInit {

  wochentage: WochentagEnum[] = Object.values(WochentagEnum);

  vorstellungen: Film[] = [];
  gewaehlterTag: WochentagEnum | undefined;

  constructor(private vorstellungService: VorstellungService, private datumService: DatumService) {
  }

  ngOnInit() {
    this.wochentage = this.datumService.getWocheBeginnendAn(new Date().toDateString());

    this.vorstellungService.holeVorstellungenFuerTag("2025-03-16").subscribe(response => {
      //
    });

  }


}
