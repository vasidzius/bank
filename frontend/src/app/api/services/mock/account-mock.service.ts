import {Injectable} from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {Account} from "../../../model/account";


@Injectable()
export class AccountMockService {

  constructor() {
  }

  /**
   * create Account by setting up balance in double form like ##.##
   *
   * @param balance only two digits after dot is allowed
   */
  public createAccountUsingPOST( balance: number ): Observable<Account> {
    return Observable.create(
      new Account(100, 1, false, 1)
    );
  }

  /**
   * Delete account by Id. In fact this operation doesn&#39;t delete record in DB but change flag &#39;deleted&#39; to TRUE
   *
   * @param accountId accountId
   */
  public deleteAccountUsingDELETE( accountId: number ): Observable<{}> {
    return Observable.create();
  }

  /**
   * Show all deleted Accounts
   *
   */
  public findAllIncludeDeletedUsingGET(): Observable<Account[]> {
    return Observable.create(
      [
        new Account(100, 1, true, 1)
      ]
    );
  }

  /**
   * view a list of all existing accounts except deleted
   *
   */
  public findAllUsingGET(): Observable<Account[]> {
    return Observable.create(
      [
        new Account(100, 1, false, 1)
      ]
    );
  }

  /**
   * Find Account by Id
   *
   * @param accountId accountId
   */
  public findUsingGET( accountId: number ): Observable<Account> {

    return Observable.create(
      new Account(100, 1, false, 1)
    );
  }

}
