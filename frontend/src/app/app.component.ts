import {Component, Inject, LOCALE_ID} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {ProgrammComponent} from './programm/components/programm-page/programm.component';
import {KartenverkaufComponent} from './kartenverkauf/components/kartenverkauf-page/kartenverkauf.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, ProgrammComponent, KartenverkaufComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  standalone: true,
})
export class AppComponent {

  constructor(@Inject(LOCALE_ID) private locale: string) {
    console.log("Current LOCALE_ID:", this.locale); // Should print: 'de-DE'
  }
}
