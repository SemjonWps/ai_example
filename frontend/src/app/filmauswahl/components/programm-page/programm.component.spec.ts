import {ComponentFixture, TestBed} from '@angular/core/testing';

import {ProgrammComponent} from './programm.component';
import {provideHttpClient, withFetch} from '@angular/common/http';

describe('ProgrammComponent', () => {
  let component: ProgrammComponent;
  let fixture: ComponentFixture<ProgrammComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [provideHttpClient(withFetch())],
      imports: [ProgrammComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(ProgrammComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
