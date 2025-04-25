import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {Zahlungsanforderung, Zahlungsbestaetigung, ZahlungStatus} from '../../../dtos/kartenverkauf';
import {GeldbetragPipe} from '../../../services/geldbetrag.pipe';

@Component({
  selector: 'app-zahlungdialog',
  imports: [
    GeldbetragPipe
  ],
  templateUrl: './zahlungdialog.component.html',
  styleUrl: './zahlungdialog.component.css'
})
export class ZahlungdialogComponent {

  @Input({required: true})
  zahlungsanforderung!: Zahlungsanforderung;

  @Output()
  onDialogGeschlossen = new EventEmitter<Zahlungsbestaetigung>();

  @ViewChild('zahlungDialogModal')
  dialogRef!: ElementRef<HTMLDialogElement>;

  oeffneDialog(): void {
    this.dialogRef.nativeElement.showModal();
  }

  schliesseDialog(): void {
    this.dialogRef.nativeElement.close();
    const zahlungsbestaetigung: Zahlungsbestaetigung = {
      zahlungsanforderung: this.zahlungsanforderung,
      status: ZahlungStatus.BEZAHLT
    };
    this.onDialogGeschlossen.emit(zahlungsbestaetigung);
  }

}
