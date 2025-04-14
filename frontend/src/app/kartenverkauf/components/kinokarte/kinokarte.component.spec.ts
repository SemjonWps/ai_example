import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KinokarteComponent } from './kinokarte.component';

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
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
