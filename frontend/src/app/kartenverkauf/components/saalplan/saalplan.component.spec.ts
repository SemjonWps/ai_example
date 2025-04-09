import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaalplanComponent } from './saalplan.component';

describe('SaalplanComponent', () => {
  let component: SaalplanComponent;
  let fixture: ComponentFixture<SaalplanComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaalplanComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaalplanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
