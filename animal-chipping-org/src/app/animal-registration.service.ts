import { Injectable } from '@angular/core';
import { AnimalCreateRequest } from './animal/animal-create-request';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AnimalResponse } from './animal/animal-response';
import { DataShareService } from './data-share.service';

@Injectable({
  providedIn: 'root'
})
export class AnimalRegistrationService {
  private apiUrl = 'http://78.153.130.215:8080/animals';
  private email: string | undefined;
  private password: string | undefined;

  constructor(private http: HttpClient, private dataShareService: DataShareService) {
    dataShareService.email.subscribe(email => this.email = email);
    dataShareService.password.subscribe(password => this.password = password);
  }

  registerAnimal(animal: AnimalCreateRequest) {
    console.log(this.email);
    console.log(this.password);
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Basic ' + btoa(`${this.email}:${this.password}`)
      })
    };

    return this.http.post<AnimalResponse>(this.apiUrl, animal, httpOptions);
  }
}
