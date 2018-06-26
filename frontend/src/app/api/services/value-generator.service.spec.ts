import { TestBed, inject } from '@angular/core/testing';

import { ValueGeneratorService } from './value-generator.service';

describe('ValueGeneratorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ValueGeneratorService]
    });
  });

  it('should be created', inject([ValueGeneratorService], (service: ValueGeneratorService) => {
    expect(service).toBeTruthy();
  }));
});
