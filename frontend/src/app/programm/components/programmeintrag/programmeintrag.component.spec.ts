import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgrammeintragComponent } from './programmeintrag.component';

describe('ProgrammeintragComponent', () => {
  let component: ProgrammeintragComponent;
  let fixture: ComponentFixture<ProgrammeintragComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProgrammeintragComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProgrammeintragComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
