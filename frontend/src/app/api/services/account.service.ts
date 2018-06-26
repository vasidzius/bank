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
    // verify required parameter 'balance' is not null or undefined
    if (balance === null || balance === undefined) {
      throw new Error('Required parameter balance was null or undefined when calling createAccountUsingPOST.');
    }
    return this.http.post<Account>(API_URL + '/accounts', balance);
  }

  /**
   * Delete account by Id. In fact this operation doesn&#39;t delete record in DB but change flag &#39;deleted&#39; to TRUE
   *
   * @param accountId accountId
   */
  public deleteAccountUsingDELETE(accountId: number): Observable<{}> {
    return this.http.delete(API_URL + '/accounts' + accountId);
  }

  /**
   * Show all deleted Accounts
   *
   */
  public findAllDeletedUsingGET(): Observable<Account[]> {
    return this.http.get<Account[]>(API_URL + '/accounts/allDeleted');
  }

  /**
   * view a list of all existing accounts except deleted
   *
   */
  public findAllUsingGET(): Observable<Account[]> {
    return this.http.get<Array<Account>>(API_URL + '/accounts');
  }

  /**
   * Find Account by Id
   *
   * @param accountId accountId
   */
  public findUsingGET(accountId: number): Observable<Account> {
    return this.http.get<Account>( API_URL + '/accounts/' + accountId);
  }

}
