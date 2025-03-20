import {Injectable} from '@angular/core';
import {WochentagEnum} from '../dtos/wochentag.enum';

@Injectable({
  providedIn: 'root'
})
export class DatumService {

  private wochentageMap: { [key: number]: WochentagEnum } = {
    0: WochentagEnum.Sonntag,
    1: WochentagEnum.Montag,
    2: WochentagEnum.Dienstag,
    3: WochentagEnum.Mittwoch,
    4: WochentagEnum.Donnerstag,
    5: WochentagEnum.Freitag,
    6: WochentagEnum.Samstag,
  };

  constructor() {
  }

  getWochentag(dateString: string): WochentagEnum {
    const weekdayIndex = new Date(dateString).getDay(); // 0 = Sunday, 1 = Monday, ..., 6 = Saturday

    return this.wochentageMap[weekdayIndex];
  }

  getWocheBeginnendAn(datum: string) {
    const weekdayIndex = new Date(datum).getDay();
    const rueckgabeWoche: WochentagEnum[] = [];

    for (let i = 0; i < 7; i++) {
      rueckgabeWoche[i] = this.wochentageMap[(weekdayIndex + i) % 7]
    }

    return rueckgabeWoche;
  }
}
