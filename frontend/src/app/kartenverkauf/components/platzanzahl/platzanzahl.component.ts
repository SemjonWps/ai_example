import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {isPresent} from '../../../common/utils';

@Component({
  selector: 'app-platzanzahl',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './platzanzahl.component.html',
  styleUrl: './platzanzahl.component.css',
  standalone: true,
})
export class PlatzanzahlComponent {

  platzanzahlControl: FormControl<number | null> = new FormControl<number | null>(null, [Validators.required, Validators.min(1)]);

  @Output() onPlatzanzahlButtonClick: EventEmitter<number> = new EventEmitter();

  uebermittlePlatzanzahl() {
    if (!isPresent(this.platzanzahlControl.value)) {
      return;
    }
    this.onPlatzanzahlButtonClick.emit(this.platzanzahlControl.value);
  }
}
