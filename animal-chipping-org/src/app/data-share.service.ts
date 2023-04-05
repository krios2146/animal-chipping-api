import { Injectable } from '@angular/core';
import { AccountRequest } from './account/account-request';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataShareService {
  private emailSource = new BehaviorSubject("");
  private passwordSource = new BehaviorSubject("");
  private idSource = new BehaviorSubject("");
  
  email = this.emailSource.asObservable();
  password = this.passwordSource.asObservable();
  id = this.idSource.asObservable();

  constructor() { }

  setEmail(email: string) {
    this.emailSource.next(email);
  }

  setId(id: string) {
    this.idSource.next(id);
  }

  setPassword(password: string) {
    this.passwordSource.next(password);
  }
}
