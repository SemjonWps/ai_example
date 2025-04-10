import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Geldbetrag} from '../../dtos/kartenverkauf';

@Component({
  selector: 'app-zahlung',
  imports: [],
  templateUrl: './zahlung.component.html',
  styleUrl: './zahlung.component.css'
})
export class ZahlungComponent {

  @Input()
  gesamtpreis!: Geldbetrag;

  @Output()
  oeffneZahlungDialog = new EventEmitter();

  weiterMitZahlung() {
    this.oeffneZahlungDialog.emit();
  }


}
