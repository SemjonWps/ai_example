import { TestBed } from '@angular/core/testing';

import { VorstellungService } from './vorstellung.service';

describe('VorstellungService', () => {
  let service: VorstellungService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VorstellungService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
