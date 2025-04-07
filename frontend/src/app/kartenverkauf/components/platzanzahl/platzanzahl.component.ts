import {Component, Input} from '@angular/core';
import {FormControl, ReactiveFormsModule} from '@angular/forms';

@Component({
  selector: 'app-platzanzahl',
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './platzanzahl.component.html',
  styleUrl: './platzanzahl.component.css'
})
export class PlatzanzahlComponent {

  @Input()
  platzanzahlControl!: FormControl<number>

}
