import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataShareService {
  private emailSource = new BehaviorSubject<string>("email");
  private passwordSource = new BehaviorSubject<string>("password");
  private idSource = new BehaviorSubject<number>(1);
  
  email = this.emailSource.asObservable();
  password = this.passwordSource.asObservable();
  id = this.idSource.asObservable();

  constructor() { }

  setEmail(email: string) {
    this.emailSource.next(email);
  }

  setId(id: number) {
    this.idSource.next(id);
  }

  setPassword(password: string) {
    this.passwordSource.next(password);
  }
}
