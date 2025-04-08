import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from '@angular/forms';

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

  @Input({required: true})
  platzanzahlControl!: FormControl<number | null>;

  uebermittlePlatzanzahl() {
  }
}
