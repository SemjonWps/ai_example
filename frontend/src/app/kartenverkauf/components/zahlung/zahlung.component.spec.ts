import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahlungComponent } from './zahlung.component';

describe('ZahlungComponent', () => {
  let component: ZahlungComponent;
  let fixture: ComponentFixture<ZahlungComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ZahlungComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ZahlungComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
