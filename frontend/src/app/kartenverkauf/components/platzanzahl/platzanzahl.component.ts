import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {isPresent} from '../../../common/utils';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-platzanzahl',
  imports: [
    ReactiveFormsModule,
    NgForOf
  ],
  templateUrl: './platzanzahl.component.html',
  styleUrl: './platzanzahl.component.css',
  standalone: true,
})
export class PlatzanzahlComponent {

  maxAnzahl: number = 10
  anzahlOptions: number[] = Array.from({length: this.maxAnzahl}, (_, i) => i + 1);

  platzanzahlControl: FormControl<number | null> = new FormControl<number | null>(null,
    [Validators.required, Validators.min(1), Validators.max(this.maxAnzahl)]);

  @Output() onPlatzanzahlButtonClick: EventEmitter<number> = new EventEmitter();

  uebermittlePlatzanzahl() {
    if (!isPresent(this.platzanzahlControl.value)) {
      return;
    }
    this.onPlatzanzahlButtonClick.emit(this.platzanzahlControl.value);
  }
}
