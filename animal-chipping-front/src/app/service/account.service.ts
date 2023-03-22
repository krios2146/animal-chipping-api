import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccountRequest } from '../model/account/account-request';
import { AccountResponse } from '../model/account/account-response';


@Injectable({
  providedIn: 'root'
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/accounts';

  constructor(private http: HttpClient) { }

  getAccountById(accountId: number): Observable<AccountResponse> {
    return this.http.get<AccountResponse>(`${this.apiUrl}/${accountId}`);
  }

  updateAccount(accountData: AccountRequest): Observable<AccountResponse> {
    return this.http.put<AccountResponse>(this.apiUrl, accountData);
  }

  deleteAccount(accountId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${accountId}`);
  }

}
