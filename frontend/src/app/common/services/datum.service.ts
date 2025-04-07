import {Injectable} from '@angular/core';
import {addDays, format, startOfWeek} from 'date-fns';

@Injectable({
  providedIn: 'root'
})
export class DatumService {

  private today: Date = new Date();

  constructor() {
  }

  getToday() {
    return this.today;
  }

  formatWochentag(date: Date): string {
    return format(date, 'EE').slice(0, 2);
  }

  getWochentage(date: Date) {
    const monday = startOfWeek(date, {weekStartsOn: 1});
    return Array.from({length: 7}, (_, i) => addDays(monday, i));
  }
}
