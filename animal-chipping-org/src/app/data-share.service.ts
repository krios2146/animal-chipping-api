import { Injectable } from '@angular/core';
import { AccountResponse } from './account/account-response';
import { AccountRequest } from './account/account-request';

@Injectable({
  providedIn: 'root'
})
export class DataShareService {
  private account: AccountRequest | undefined;
  private accountId: number | undefined;

  constructor() { }

  setAccount(account: AccountRequest) {
    this.account = account;
  }

  getAccount(): AccountRequest {
    return this.account!;
  }

  setAccountId(accountId: number) {
    this.accountId = accountId;
  }

  getAccountId(): number {
    return this.accountId!;
  }
}
