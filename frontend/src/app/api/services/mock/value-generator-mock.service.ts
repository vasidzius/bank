import {Injectable} from '@angular/core';
import {Observable} from "rxjs";


@Injectable()
export class ValueGeneratorMockService {

  constructor() {
  }

  public generate(): Observable<Object> {
    return Observable.create();
  }
}
