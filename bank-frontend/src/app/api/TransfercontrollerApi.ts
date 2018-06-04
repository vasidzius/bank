/**
 * Bank
 * \"Imitation Bank Processing System\"
 *
 * OpenAPI spec version: 1.0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {Headers, Http, RequestOptionsArgs, Response, URLSearchParams} from '@angular/http';
import {Injectable, Optional} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import * as models from '../model/models';
import 'rxjs/Rx';

/* tslint:disable:no-unused-variable member-ordering */

'use strict';

@Injectable()
export class TransfercontrollerApi {
  public defaultHeaders: Headers = new Headers();
  protected basePath = 'https://localhost:8080/bank';

  constructor(protected http: Http, @Optional() basePath: string) {
    if (basePath) {
      this.basePath = basePath;
    }
  }

  /**
   * create Transfer. There are three possibilities: between two account, increasing (field fromAccountId is null), decreasing (field toAccountId is null)
   *
   * @param transferRequest transferRequest
   */
  public createUsingPOST(transferRequest: models.TransferRequest, extraHttpRequestParams?: any): Observable<models.Transfer> {
    const path = this.basePath + '/transfers';

    let queryParameters = new URLSearchParams();
    let headerParams = this.defaultHeaders;
    // verify required parameter 'transferRequest' is not null or undefined
    if (transferRequest === null || transferRequest === undefined) {
      throw new Error('Required parameter transferRequest was null or undefined when calling createUsingPOST.');
    }
    let requestOptions: RequestOptionsArgs = {
      method: 'POST',
      headers: headerParams,
      search: queryParameters
    };
    requestOptions.body = JSON.stringify(transferRequest);

    return this.http.request(path, requestOptions)
      .map((response: Response) => {
        if (response.status === 204) {
          return undefined;
        } else {
          return response.json();
        }
      });
  }

  /**
   * view a list of all existing Transfers
   *
   */
  public findAllUsingGET1(extraHttpRequestParams?: any): Observable<Array<models.Transfer>> {
    const path = this.basePath + '/transfers';

    let queryParameters = new URLSearchParams();
    let headerParams = this.defaultHeaders;
    let requestOptions: RequestOptionsArgs = {
      method: 'GET',
      headers: headerParams,
      search: queryParameters
    };

    return this.http.request(path, requestOptions)
      .map((response: Response) => {
        if (response.status === 204) {
          return undefined;
        } else {
          return response.json();
        }
      });
  }

  /**
   * Find Transfer by Id
   *
   * @param transferId transferId
   */
  public findUsingGET1(transferId: number, extraHttpRequestParams?: any): Observable<models.Transfer> {
    const path = this.basePath + '/transfers/{transferId}'
      .replace('{' + 'transferId' + '}', String(transferId));

    let queryParameters = new URLSearchParams();
    let headerParams = this.defaultHeaders;
    // verify required parameter 'transferId' is not null or undefined
    if (transferId === null || transferId === undefined) {
      throw new Error('Required parameter transferId was null or undefined when calling findUsingGET1.');
    }
    let requestOptions: RequestOptionsArgs = {
      method: 'GET',
      headers: headerParams,
      search: queryParameters
    };

    return this.http.request(path, requestOptions)
      .map((response: Response) => {
        if (response.status === 204) {
          return undefined;
        } else {
          return response.json();
        }
      });
  }

}