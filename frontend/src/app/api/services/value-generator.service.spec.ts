import { TestBed, inject } from '@angular/core/testing';

import { ValueGeneratorService } from './value-generator.service';
import {ValueGeneratorMockService} from "./mock/value-generator-mock.service";

describe('ValueGeneratorService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: ValueGeneratorService,
          useClass: ValueGeneratorMockService
        }
      ]
    });
  });

  it('should be created', inject([ValueGeneratorService], (service: ValueGeneratorService) => {
    expect(service).toBeTruthy();
  }));
});
