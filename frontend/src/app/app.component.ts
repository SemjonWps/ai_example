import {Component, Inject, LOCALE_ID} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ProgrammComponent} from './programm/components/programm-page/programm.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ProgrammComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  constructor(@Inject(LOCALE_ID) private locale: string) {
    console.log("Current LOCALE_ID:", this.locale); // Should print: 'de-DE'
  }
}
