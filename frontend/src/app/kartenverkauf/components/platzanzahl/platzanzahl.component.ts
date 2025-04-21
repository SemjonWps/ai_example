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

  platzanzahlControl: FormControl<number | null> = new FormControl<number | null>(null, [Validators.required, Validators.min(1)]);

  @Output() onPlatzanzahlButtonClick: EventEmitter<number> = new EventEmitter();

  maxAnzahl: number = 10
  steps: number[] = Array.from({length: this.maxAnzahl}, (_, i) => i + 1);

  uebermittlePlatzanzahl() {
    if (!isPresent(this.platzanzahlControl.value)) {
      return;
    }
    this.onPlatzanzahlButtonClick.emit(this.platzanzahlControl.value);
  }
}
