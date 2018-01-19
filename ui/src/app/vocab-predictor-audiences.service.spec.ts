import { TestBed, inject } from '@angular/core/testing';

import { VocabPredictorAudiencesService } from './vocab-predictor-audiences.service';

describe('VocabPredictorAudiencesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [VocabPredictorAudiencesService]
    });
  });

  it('should be created', inject([VocabPredictorAudiencesService], (service: VocabPredictorAudiencesService) => {
    expect(service).toBeTruthy();
  }));
});
