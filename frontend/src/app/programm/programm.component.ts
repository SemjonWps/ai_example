import {Component, OnInit} from '@angular/core';
import {CurrencyPipe, DatePipe, NgForOf, NgIf} from "@angular/common";
import {WochentagEnum} from './wochentag.enum';
import {Vorstellung, VorstellungService} from './vorstellung.service';
import {SaalEnum} from './saal.enum';
import {DatumService} from './datum.service';
import {VorstellungComponent} from '../vorstellung/vorstellung.component';

@Component({
  selector: 'app-programm',
  imports: [
    CurrencyPipe,
    DatePipe,
    NgForOf,
    NgIf,
    VorstellungComponent
  ],
  templateUrl: './programm.component.html',
  styleUrl: './programm.component.css'
})
export class ProgrammComponent implements OnInit{

  wochentage: WochentagEnum[] = Object.values(WochentagEnum);

  vorstellungen: Vorstellung[] = [];
  gewaehlterTag: WochentagEnum | undefined;

  saele: SaalEnum[] = Object.values(SaalEnum);

  constructor(private vorstellungService: VorstellungService, private datumService: DatumService) {
  }

  ngOnInit() {
    this.vorstellungService.findAll().subscribe((data: Vorstellung[]): void => {
      this.vorstellungen = data;
      this.gewaehlterTag = this.datumService.getWochentag(this.vorstellungen[0].anfangszeit);
      this.wochentage = this.datumService.getWocheBeginnendAn(this.vorstellungen[0].anfangszeit);
      console.log(this.vorstellungen);
    });
  }

}
