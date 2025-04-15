import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Geldbetrag} from '../../dtos/kartenverkauf';
import {GeldbetragPipe} from '../../services/geldbetrag.pipe';

@Component({
  selector: 'app-zahlung',
  imports: [
    GeldbetragPipe
  ],
  templateUrl: './zahlung.component.html',
  styleUrl: './zahlung.component.css'
})
export class ZahlungComponent {

  @Input()
  gesamtpreis!: Geldbetrag;

  @Output()
  oeffneZahlungDialog = new EventEmitter();

  preisInEuro: string = "";

  weiterMitZahlung() {
    this.oeffneZahlungDialog.emit();
  }
}
