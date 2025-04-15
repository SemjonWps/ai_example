import {ComponentFixture, TestBed} from '@angular/core/testing';

import {KinokarteComponent} from './kinokarte.component';
import {Angebot} from '../../dtos/kartenverkauf';

describe('KinokarteComponent', () => {
  let component: KinokarteComponent;
  let fixture: ComponentFixture<KinokarteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KinokarteComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(KinokarteComponent);
    component = fixture.componentInstance;

    component.angebot = {
      gesamtpreis: {betragInEuroCent: 20},
      platzDtos: [
        {reihe: {reihennummer: 1}, sitz: {platznummer: 2}},
        {reihe: {reihennummer: 1}, sitz: {platznummer: 3}},
      ]
    } as Angebot;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('createPlaetzeString', () => {
    it('should return correct string with valid angebot', () => {

      const expectedResult = "Reihe 1, Platz 2, 3";

      const result: string = component.createPlaetzeString()

      expect(result).toEqual(expectedResult);
    })

    it('should throw exception with invalid angebot', () => {
      component.angebot.platzDtos = [];

      expect(() => {
        component.createPlaetzeString();
      }).toThrowError('Angebot enthält keine Plätze');
    })
  })
});
