import { TestBed } from '@angular/core/testing';

import { AnimalRegistrationService } from './animal-registration.service';

describe('AnimalRegistrationService', () => {
  let service: AnimalRegistrationService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnimalRegistrationService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
