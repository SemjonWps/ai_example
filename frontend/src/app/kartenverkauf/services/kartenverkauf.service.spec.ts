import { TestBed } from '@angular/core/testing';

import { KartenverkaufService } from './kartenverkauf.service';

describe('KartenverkaufService', () => {
  let service: KartenverkaufService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(KartenverkaufService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
