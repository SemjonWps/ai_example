import {ComponentFixture, TestBed} from '@angular/core/testing';

import {VorstellungenComponent} from './vorstellungen.component';

describe('VorstellungComponent', () => {
  let component: VorstellungenComponent;
  let fixture: ComponentFixture<VorstellungenComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VorstellungenComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(VorstellungenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
