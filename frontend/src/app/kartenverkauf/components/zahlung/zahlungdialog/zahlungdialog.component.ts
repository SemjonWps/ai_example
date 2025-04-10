import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-zahlungdialog',
  imports: [],
  templateUrl: './zahlungdialog.component.html',
  styleUrl: './zahlungdialog.component.css'
})
export class ZahlungdialogComponent {

  @Output() close = new EventEmitter<void>();

  closeModal(): void {
    this.close.emit();
  }

}
