import {Injectable} from '@angular/core';
import {Error} from "tslint/lib/error";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {Account} from "../../model/account";
import {environment} from "../../../environments/environment";

const API_URL = environment.apiBankUrl;

@Injectable()
export class AccountService {

  constructor(private http: HttpClient) {
  }

  /**
   * create Account by setting up balance in double form like ##.##
   *
   * @param balance only two digits after dot is allowed
   */
  public createAccountUsingPOST(balance: number): Observable<Account> {
    // const path = API_URL + '/accounts';

    // let queryParameters = new HttpParams();
    // let headerParams = this.defaultHeaders;
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

    return this.http.post<Account>(API_URL + '/accounts', balance);

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
  public deleteAccountUsingDELETE(accountId: number): Observable<{}> {
    const path = API_URL + '/accounts/{accountId}'
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

    return this.http.delete(path);

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
  public findAllDeletedUsingGET(): Observable<Account[]> {
    const path = API_URL + '/accounts/allDeleted';

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
  public findAllUsingGET(): Observable<Account[]> {
    const path = API_URL + '/accounts';

    // let queryParameters = new URLSearchParams();
    // let headerParams = this.defaultHeaders;
    // let requestOptions: RequestOptionsArgs = {
    //     method: 'GET',
    //     headers: headerParams,
    //     search: queryParameters
    // };

    return this.http.get<Array<Account>>(path);

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
  public findUsingGET(accountId: number): Observable<Account> {
    const path = API_URL + '/accounts/{accountId}'
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
