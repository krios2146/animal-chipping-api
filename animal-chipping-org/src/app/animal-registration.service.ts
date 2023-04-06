import { Injectable } from '@angular/core';
import { AnimalCreateRequest } from './animal/animal-create-request';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AnimalResponse } from './animal/animal-response';

@Injectable({
  providedIn: 'root'
})
export class AnimalRegistrationService {
  private apiUrl = 'http://78.153.130.215:8080/animals';
  private email: string = "root@email.com";
  private password: string = "root";

  constructor(private http: HttpClient) {}

  registerAnimal(animal: AnimalCreateRequest) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Basic ' + btoa(`${this.email}:${this.password}`)
      })
    };

    return this.http.post<AnimalResponse>(this.apiUrl, animal, httpOptions);
  }
}
