import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";

const API_URL = environment.apiBankUrl;

@Injectable()
export class ValueGeneratorService {

  constructor( private http: HttpClient ) {
  }

  public generate(
    accountsNumber: number = 10,
    threadNumberBetweenTwo: number = 10,
    transfersBetweenTwo: number = 10,
    accountsToDelete: number = 5,
    transfersIncreasing: number = 5,
    transfersDecreasing: number = 5 ): Observable<Object> {
    let params = new HttpParams()
      .set('accountsNumber', accountsNumber.toString())
      .set('threadNumberBetweenTwo', threadNumberBetweenTwo.toString())
      .set('transfersBetweenTwo', transfersBetweenTwo.toString())
      .set('accountsToDelete', accountsToDelete.toString())
      .set('transfersIncreasing', transfersIncreasing.toString())
      .set('transfersDecreasing', transfersDecreasing.toString());
    return this.http.post<Object>(API_URL + '/generateRandomValues', null, {params})
  }
}
