import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ProgrammComponent} from './programm/components/programm/programm.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ProgrammComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
}
