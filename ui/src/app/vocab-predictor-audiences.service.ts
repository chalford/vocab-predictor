import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';

@Injectable()
export class VocabPredictorAudiencesService {

  constructor() { }

  getAudiences(): Observable<string[]> {
    return of(['Eastenders', 'colours']);
  }

}
