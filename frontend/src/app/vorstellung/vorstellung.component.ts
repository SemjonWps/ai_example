import {Component, Input} from '@angular/core';
import {CurrencyPipe, DatePipe} from '@angular/common';
import {Vorstellung} from '../programm/vorstellung.service';

@Component({
  selector: 'app-vorstellung',
  imports: [
    CurrencyPipe,
    DatePipe
  ],
  templateUrl: './vorstellung.component.html',
  styleUrl: './vorstellung.component.css'
})
export class VorstellungComponent {
  @Input({required: true})
  vorstellung!: Vorstellung;

  constructor() {
  }



}
