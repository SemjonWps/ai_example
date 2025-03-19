import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {WochenplanComponent} from './wochenplan/wochenplan.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, WochenplanComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
}
