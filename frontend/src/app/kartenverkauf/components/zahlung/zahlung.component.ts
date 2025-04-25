import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {
  Vorstellung,
  Zahlungsanforderung,
  Zahlungsbestaetigung,
  ZusammenhaengendePlaetze
} from '../../dtos/kartenverkauf';
import {GeldbetragPipe} from '../../services/geldbetrag.pipe';
import {ZahlungdialogComponent} from './zahlungdialog/zahlungdialog.component';
import {KartenverkaufService} from '../../services/kartenverkauf.service';

@Component({
  selector: 'app-zahlung',
  imports: [
    GeldbetragPipe,
    ZahlungdialogComponent
  ],
  templateUrl: './zahlung.component.html',
  styleUrl: './zahlung.component.css'
})
export class ZahlungComponent implements OnInit {

  @Input({required: true})
  vorstellung!: Vorstellung;

  @Input({required: true})
  plaetze!: ZusammenhaengendePlaetze;

  @Output()
  onZahlungBestaetigt: EventEmitter<Zahlungsbestaetigung> = new EventEmitter();

  zahlungsanforderung: Zahlungsanforderung | undefined;

  @ViewChild('zahlungDialog')
  zahlungDialog!: ZahlungdialogComponent;


  constructor(private kartenverkaufService: KartenverkaufService) {
  }

  ngOnInit(): void {
    this.kartenverkaufService.holeZahlungsanforderung(this.vorstellung.uuid, this.plaetze).subscribe((zahlungsanforderung: Zahlungsanforderung) => {
      this.zahlungsanforderung = zahlungsanforderung;
    });
  }

  oeffneZahlungDialog() {
    this.zahlungDialog.oeffneDialog()
  }

  zahlungDialogGeschlossen(zahlungsbestaetigung: Zahlungsbestaetigung) {
    this.onZahlungBestaetigt.emit(zahlungsbestaetigung);
  }

}
