import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Term } from './term';
import { Terms } from './terms';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable()
export class VocabPredictorRetrieverService {

  private termUrl = 'https://qgea6apuw4.execute-api.eu-west-1.amazonaws.com/dev/predictions';
  TERMS: Term[] = [
  {'value': 'rowlock', 'frequency': 0},
  {'value': 'bowsprit', 'frequency': 0},
  {'value': 'mast', 'frequency': 0},
  {'value': 'bumpkin', 'frequency': 0}
];

  constructor(  private http: HttpClient) { }

  getTerms(programme): Observable<Terms> {
    const params = new HttpParams().set('audience', programme);
    return this.http.get<Terms>(this.termUrl, { params: params });
  }

}
