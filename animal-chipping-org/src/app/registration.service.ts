import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AccountRequest } from './account/account-request';
import { AccountResponse } from './account/account-response';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {
  private apiUrl = 'http://78.153.130.215:8080/registration';

  constructor(private http: HttpClient) { }

  register(accountRequest: AccountRequest): Observable<AccountResponse> {
    return this.http.post<AccountResponse>(this.apiUrl, accountRequest);
  }
}
