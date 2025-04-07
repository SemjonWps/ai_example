import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VorstellungComponent } from './vorstellung.component';

describe('VorstellungComponent', () => {
  let component: VorstellungComponent;
  let fixture: ComponentFixture<VorstellungComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VorstellungComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VorstellungComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
