import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";

const API_URL = environment.apiBankUrl;

@Injectable()
export class ValueGeneratorService {

  constructor( private http: HttpClient ) {
  }


  public generate(): Observable<Object> {
    let params = new HttpParams()
      .set('accountsNumber', '100')
      .set('threadNumberBetweenTwo', '10')
      .set('transfersBetweenTwo', '10')
      .set('accountsToDelete', '5')
      .set('transfersIncreasing', '5')
      .set('transfersDecreasing', '5');
    return this.http.post<Object>(API_URL + '/generateRandomValues', null, {params})
  }
}
