import {TestBed} from '@angular/core/testing';

import {DatumService} from './datum.service';
import {WochentagEnum} from './wochentag.enum';

describe('DatumService', () => {
  let service: DatumService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DatumService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getWochentag', () => {
    it('should return sunday from date', () => {
      const datum = "2025-03-16 16:30:00";

      const result = service.getWochentag(datum);

      expect(result).toEqual(WochentagEnum.Sonntag);
    });

    it('should return tuesday from date', () => {
      const datum = "2025-03-18 16:30:00";

      const result = service.getWochentag(datum);

      expect(result).toEqual(WochentagEnum.Dienstag);
    });
  });

  describe('getWocheBeginnendAn', () => {
    it('should return week of days beginning sunday', () => {
      const datum = "2025-03-16 16:30:00";
      const expectedResult: WochentagEnum[] = [
        WochentagEnum.Sonntag,
        WochentagEnum.Montag,
        WochentagEnum.Dienstag,
        WochentagEnum.Mittwoch,
        WochentagEnum.Donnerstag,
        WochentagEnum.Freitag,
        WochentagEnum.Samstag
      ];

      const result = service.getWocheBeginnendAn(datum);

      expect(result).toEqual(expectedResult);
    });

    it('should return week of days beginning tuesday', () => {
      const datum = "2025-03-18 16:30:00";
      const expectedResult: WochentagEnum[] = [
        WochentagEnum.Dienstag,
        WochentagEnum.Mittwoch,
        WochentagEnum.Donnerstag,
        WochentagEnum.Freitag,
        WochentagEnum.Samstag,
        WochentagEnum.Sonntag,
        WochentagEnum.Montag
      ];

      const result = service.getWocheBeginnendAn(datum);

      expect(result).toEqual(expectedResult);
    });
  })

});
