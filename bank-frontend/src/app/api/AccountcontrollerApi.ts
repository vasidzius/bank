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

import {Headers} from '@angular/http';
import {Injectable, Optional} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import * as models from '../model/models';
import {Account} from '../model/models';
import 'rxjs/Rx';
import {Error} from "tslint/lib/error";
import {HttpClient, HttpParams} from "@angular/common/http";

/* tslint:disable:no-unused-variable member-ordering */

'use strict';

@Injectable()
export class AccountcontrollerApi {
  public defaultHeaders: Headers = new Headers();
  protected basePath = 'https://localhost:8080/bank';

  constructor(protected http: HttpClient, @Optional() basePath: string) {
    if (basePath) {
      this.basePath = basePath;
    }
  }

  /**
   * create Account by setting up balance in double form like ##.##
   *
   * @param balance only two digits after dot is allowed
   */
  public createAccountUsingPOST(balance: number): Observable<models.Account> {
    const path = this.basePath + '/accounts';

    let queryParameters = new HttpParams();
    let headerParams = this.defaultHeaders;
    // verify required parameter 'balance' is not null or undefined
    if (balance === null || balance === undefined) {
      throw new Error('Required parameter balance was null or undefined when calling createAccountUsingPOST.');
    }
    // let requestOptions: RequestOptionsArgs = {
    //     method: 'POST',
    //     headers: headerParams,
    //     search: queryParameters
    // };
    // requestOptions.body = JSON.stringify(balance);

    return this.http.post(path, balance);

    // return this.http.request(path, requestOptions)
    //     .map((response: Response) => {
    //         if (response.status === 204) {
    //             return undefined;
    //         } else {
    //             return response.json();
    //         }
    //     });
  }

  /**
   * Delete account by Id. In fact this operation doesn&#39;t delete record in DB but change flag &#39;deleted&#39; to TRUE
   *
   * @param accountId accountId
   */
  public deleteAccountUsingDELETE(accountId: number, extraHttpRequestParams?: any): Observable<{}> {
    const path = this.basePath + '/accounts/{accountId}'
      .replace('{' + 'accountId' + '}', String(accountId));

    // let queryParameters = new URLSearchParams();
    // let headerParams = this.defaultHeaders;
    // // verify required parameter 'accountId' is not null or undefined
    // if (accountId === null || accountId === undefined) {
    //     throw new Error('Required parameter accountId was null or undefined when calling deleteAccountUsingDELETE.');
    // }
    // let requestOptions: RequestOptionsArgs = {
    //     method: 'DELETE',
    //     headers: headerParams,
    //     search: queryParameters
    // };

    return this.http.get(path);

    // return this.http.request(path, requestOptions)
    //     .map((response: Response) => {
    //         if (response.status === 204) {
    //             return undefined;
    //         } else {
    //             return response.json();
    //         }
    //     });
  }

  /**
   * Show all deleted Accounts
   *
   */
  public findAllDeletedUsingGET(extraHttpRequestParams?: any): Observable<Array<models.Account>> {
    const path = this.basePath + '/accounts/allDeleted';

    // let queryParameters = new URLSearchParams();
    // let headerParams = this.defaultHeaders;
    // let requestOptions: RequestOptionsArgs = {
    //     method: 'GET',
    //     headers: headerParams,
    //     search: queryParameters
    // };

    return this.http.get<Account[]>(path);

    // return this.http.request(path, requestOptions)
    //     .map((response: Response) => {
    //         if (response.status === 204) {
    //             return undefined;
    //         } else {
    //             return response.json();
    //         }
    //     });
  }

  /**
   * view a list of all existing accounts except deleted
   *
   */
  public findAllUsingGET(): Observable<Array<models.Account>> {
    const path = this.basePath + '/accounts';

    // let queryParameters = new URLSearchParams();
    // let headerParams = this.defaultHeaders;
    // let requestOptions: RequestOptionsArgs = {
    //     method: 'GET',
    //     headers: headerParams,
    //     search: queryParameters
    // };

    return this.http.get<Account[]>(path);

    // return this.http.request(path, requestOptions)
    //     .map((response: Response) => {
    //         if (response.status === 204) {
    //             return undefined;
    //         } else {
    //             return response.json();
    //         }
    //     });
  }

  /**
   * Find Account by Id
   *
   * @param accountId accountId
   */
  public findUsingGET(accountId: number, extraHttpRequestParams?: any): Observable<models.Account> {
    const path = this.basePath + '/accounts/{accountId}'
      .replace('{' + 'accountId' + '}', String(accountId));

    // let queryParameters = new URLSearchParams();
    // let headerParams = this.defaultHeaders;
    // // verify required parameter 'accountId' is not null or undefined
    // if (accountId === null || accountId === undefined) {
    //     throw new Error('Required parameter accountId was null or undefined when calling findUsingGET.');
    // }
    // let requestOptions: RequestOptionsArgs = {
    //     method: 'GET',
    //     headers: headerParams,
    //     search: queryParameters
    // };

    return this.http.get<Account>(path);

    // return this.http.request(path, requestOptions)
    //     .map((response: Response) => {
    //         if (response.status === 204) {
    //             return undefined;
    //         } else {
    //             return response.json();
    //         }
    //     });
  }

}
