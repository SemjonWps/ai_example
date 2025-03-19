import { Injectable } from '@angular/core';
import {WochentagEnum} from './wochentag.enum';

@Injectable({
  providedIn: 'root'
})
export class DatumService {

  constructor() { }

  getWochentag(dateString: string): WochentagEnum {
    const date = new Date(dateString);
    const weekdayIndex = date.getDay(); // 0 = Sunday, 1 = Monday, ..., 6 = Saturday
    const wochentageMap: { [key: number]: WochentagEnum } = {
      0: WochentagEnum.Sonntag,
      1: WochentagEnum.Montag,
      2: WochentagEnum.Dienstag,
      3: WochentagEnum.Mittwoch,
      4: WochentagEnum.Donnerstag,
      5: WochentagEnum.Freitag,
      6: WochentagEnum.Samstag,
    };

    return wochentageMap[weekdayIndex];
  }
}
