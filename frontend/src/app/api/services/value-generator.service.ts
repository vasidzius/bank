import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {Observable} from "rxjs";
import {ValueGeneratorRequest} from "../../model/valueGeneratorRequest";

const API_URL = environment.apiBankUrl;

@Injectable()
export class ValueGeneratorService {

  constructor( private http: HttpClient ) {
  }

  public generate(
    valueGeneratorRequest: ValueGeneratorRequest): Observable<Object> {
    let params = new HttpParams()
      .set('accountsNumber', valueGeneratorRequest.accountsNumber.toString())
      .set('threadNumberBetweenTwo', valueGeneratorRequest.threadNumberBetweenTwo.toString())
      .set('transfersBetweenTwo', valueGeneratorRequest.transfersBetweenTwo.toString())
      .set('accountsToDelete', valueGeneratorRequest.accountsToDelete.toString())
      .set('transfersIncreasing', valueGeneratorRequest.transfersIncreasing.toString())
      .set('transfersDecreasing', valueGeneratorRequest.transfersDecreasing.toString());
    return this.http.post<Object>(API_URL + '/generateRandomValues', null, {params})
  }
}
