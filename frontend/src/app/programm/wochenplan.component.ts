import {Component, OnInit} from '@angular/core';
import {Vorstellung, VorstellungService} from './vorstellung.service';
import {WochentagEnum} from './wochentag.enum';
import {DatumService} from './datum.service';
import {CurrencyPipe, DatePipe, NgFor, NgIf} from '@angular/common';
import {SaalEnum} from './saal.enum';

@Component({
  selector: 'app-wochenplan',
  imports: [
    DatePipe,
    CurrencyPipe,
    NgFor,
    NgIf,
  ],
  templateUrl: './wochenplan.component.html',
  styleUrl: './wochenplan.component.css'
})
export class WochenplanComponent implements OnInit{
  title: string = 'frontend';
  wochentage: WochentagEnum[] = Object.values(WochentagEnum);

  vorstellungen: Vorstellung[] = [];
  saele: SaalEnum[] = Object.values(SaalEnum);

  constructor(private vorstellungService: VorstellungService, private datumService: DatumService) {
  }

  ngOnInit() {
    this.vorstellungService.findAll().subscribe((data: Vorstellung[]): void => {
      this.vorstellungen = data;
      console.log(this.vorstellungen);
    });
  }

  isSameDay(datum: string, wochentag: WochentagEnum) {
    return this.datumService.getWochentag(datum) === wochentag;
  }
}
