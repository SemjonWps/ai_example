import {Routes} from '@angular/router';
import {ProgrammComponent} from './filmauswahl/components/programm-page/programm.component';
import {KartenverkaufComponent} from './kartenverkauf/components/kartenverkauf-page/kartenverkauf.component';
import {FilmHinzufuegenPageComponent} from './filmauswahl/components/film-hinzufuegen-page/film-hinzufuegen-page.component';

export const routes: Routes = [
  {path: '', redirectTo: 'programm', pathMatch: "full"},
  {path: 'programm', component: ProgrammComponent},
  {path: 'kartenverkauf/:vorstellungId', component: KartenverkaufComponent},
  {path: 'film-hinzufuegen', component: FilmHinzufuegenPageComponent},
];
