import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {TransferRequest} from "../../model/transferRequest";
import {Observable} from "rxjs/internal/Observable";
import {Transfer} from "../../model/transfer";

const API_URL = environment.apiBankUrl;

@Injectable()
export class TransferService {

  constructor(private http: HttpClient) {
  }

  /**
   * create Transfer. There are three possibilities: between two account, increasing (field fromAccountId is null), decreasing (field toAccountId is null)
   *
   * @param transferRequest transferRequest
   */
  public createUsingPOST(transferRequest: TransferRequest): Observable<Transfer> {
    // const path = API_URL + '/transfers';

    // let queryParameters = new URLSearchParams();
    // let headerParams = this.defaultHeaders;

    // verify required parameter 'transferRequest' is not null or undefined
    if (transferRequest === null || transferRequest === undefined) {
      throw new Error('Required parameter transferRequest was null or undefined when calling createUsingPOST.');
    }
    // let requestOptions: RequestOptionsArgs = {
    //   method: 'POST',
    //   headers: headerParams,
    //   search: queryParameters
    // };
    // requestOptions.body = JSON.stringify(transferRequest);

    return this.http.post<Transfer>(API_URL + '/transfers', transferRequest)

    // return this.http.request(path, requestOptions)
    //   .map((response: Response) => {
    //     if (response.status === 204) {
    //       return undefined;
    //     } else {
    //       return response.json();
    //     }
    //   });
  }

  /**
   * view a list of all existing Transfers
   *
   */
  public findAllTransfers(): Observable<Transfer[]> {

    // let queryParameters = new URLSearchParams();
    // let headerParams = this.defaultHeaders;
    // let requestOptions: RequestOptionsArgs = {
    //   method: 'GET',
    //   headers: headerParams,
    //   search: queryParameters
    // };
    //
    // return this.http.request(path, requestOptions)
    //   .map((response: Response) => {
    //     if (response.status === 204) {
    //       return undefined;
    //     } else {
    //       return response.json();
    //     }
    //   });

    return this.http.get<Transfer[]>(API_URL + '/transfers');
  }

  /**
   * Find Transfer by Id
   *
   * @param transferId transferId
   */
  public findTransferById(transferId: number): Observable<Transfer> {
    const path = API_URL + '/transfers/{transferId}'
      .replace('{' + 'transferId' + '}', String(transferId));

    // let queryParameters = new URLSearchParams();
    // let headerParams = this.defaultHeaders;

    // verify required parameter 'transferId' is not null or undefined
    if (transferId === null || transferId === undefined) {
      throw new Error('Required parameter transferId was null or undefined when calling findUsingGET1.');
    }
    // let requestOptions: RequestOptionsArgs = {
    //   method: 'GET',
    //   headers: headerParams,
    //   search: queryParameters
    // };
    //
    // return this.http.request(path, requestOptions)
    //   .map((response: Response) => {
    //     if (response.status === 204) {
    //       return undefined;
    //     } else {
    //       return response.json();
    //     }
    //   });

    return this.http.get<Transfer>(API_URL + '/transfers/' + transferId);
  }

}
