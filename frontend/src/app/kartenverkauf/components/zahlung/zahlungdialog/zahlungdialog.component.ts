import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Angebot, Vorstellung} from '../../../dtos/kartenverkauf';
import {DatePipe} from '@angular/common';
import {GeldbetragPipe} from '../../../services/geldbetrag.pipe';

@Component({
  selector: 'app-zahlungdialog',
  imports: [
    DatePipe,
    GeldbetragPipe
  ],
  templateUrl: './zahlungdialog.component.html',
  styleUrl: './zahlungdialog.component.css'
})
export class ZahlungdialogComponent {
  @Input()
  angebot: Angebot | undefined;

  @Input()
  vorstellung: Vorstellung | undefined;

  @Output() schliesse = new EventEmitter<void>();

  schliesseDialog(): void {
    this.schliesse.emit();
  }

}
