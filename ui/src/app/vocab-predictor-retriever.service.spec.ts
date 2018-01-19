import { TestBed, inject } from '@angular/core/testing';

import { VocabPredictorRetrieverService } from './vocab-predictor-retriever.service';

describe('VocabPredictorRetrieverService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [VocabPredictorRetrieverService]
    });
  });

  it('should be created', inject([VocabPredictorRetrieverService], (service: VocabPredictorRetrieverService) => {
    expect(service).toBeTruthy();
  }));
});
