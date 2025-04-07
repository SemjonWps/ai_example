import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KartenverkaufComponent } from './kartenverkauf.component';

describe('KartenverkaufComponent', () => {
  let component: KartenverkaufComponent;
  let fixture: ComponentFixture<KartenverkaufComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KartenverkaufComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KartenverkaufComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
