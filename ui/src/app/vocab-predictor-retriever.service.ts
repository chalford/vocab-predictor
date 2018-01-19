import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { Term } from './term';
import { Terms } from './terms';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable()
export class VocabPredictorRetrieverService {

  private termUrl = 'https://qgea6apuw4.execute-api.eu-west-1.amazonaws.com/dev/predictions';

  constructor(  private http: HttpClient) { }

  getTerms(programme, text): Observable<Terms> {
    const params = new HttpParams().set(
      'audience', programme).set(
      'text', text
    );
    return this.http.get<Terms>(this.termUrl, { params: params });
  }

}
