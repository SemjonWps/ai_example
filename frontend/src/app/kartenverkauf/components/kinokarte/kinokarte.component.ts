import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Kinokarte, Zahlungsbestaetigung} from '../../dtos/kartenverkauf';
import {KartenverkaufService} from '../../services/kartenverkauf.service';
import {DatePipe, NgForOf} from '@angular/common';
import {GeldbetragPipe} from '../../services/geldbetrag.pipe';

@Component({
  selector: 'app-kinokarte',
  imports: [
    NgForOf,
    DatePipe,
    GeldbetragPipe
  ],
  templateUrl: './kinokarte.component.html',
  styleUrl: './kinokarte.component.css'
})
export class KinokarteComponent implements OnInit {

  @Input()
  zahlungsbestaetigung!: Zahlungsbestaetigung;

  @Output()
  onKinokartenGedruckt: EventEmitter<Kinokarte[]> = new EventEmitter();

  kinokarten: Kinokarte[] | undefined;

  constructor(private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.kartenverkaufService.erstelleKinokarten(this.zahlungsbestaetigung).subscribe(
      (data: Kinokarte[]) => {
        this.kinokarten = data;
      }
    )
  }

  druckeKinokarten() {
    this.onKinokartenGedruckt.emit(this.kinokarten);
  }

}
