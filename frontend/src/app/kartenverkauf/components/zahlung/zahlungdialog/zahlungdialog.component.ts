import {Component, EventEmitter, Input, Output} from '@angular/core';
import {AngebotDto, VorstellungDto} from '../../../dtos/kartenverkauf';
import {DatePipe} from '@angular/common';

@Component({
  selector: 'app-zahlungdialog',
  imports: [
    DatePipe
  ],
  templateUrl: './zahlungdialog.component.html',
  styleUrl: './zahlungdialog.component.css'
})
export class ZahlungdialogComponent {
  @Input()
  angebot: AngebotDto | undefined;

  @Input()
  vorstellung: VorstellungDto | undefined;

  @Output() schliesse = new EventEmitter<void>();

  schliesseDialog(): void {
    this.schliesse.emit();
  }

}
