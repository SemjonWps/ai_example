import {Routes} from '@angular/router';
import {ProgrammComponent} from './programm/components/programm-page/programm.component';

export const routes: Routes = [
  {path: '', redirectTo: 'programm', pathMatch: "full"},
  {path: 'programm', component: ProgrammComponent}
];
