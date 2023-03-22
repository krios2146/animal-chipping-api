import { AnimalCreateRequest } from './../model/animal-create-request';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AnimalResponse } from '../model/animal-response';
import { AnimalUpdateRequest } from '../model/animal-update-request';

@Injectable({
  providedIn: 'root',
})
export class AnimalService {
  private apiUrl = 'http://localhost:8080/animals';

  constructor(private http: HttpClient) {}

  getAnimalById(animalId: number): Observable<AnimalResponse> {
    return this.http.get<AnimalResponse>(`${this.apiUrl}/${animalId}`);
  }

  createAnimal(animal: AnimalCreateRequest): Observable<AnimalResponse> {
    return this.http.post<AnimalResponse>(`${this.apiUrl}`, animal);
  }

  updateAnimal(updatedAnimal: AnimalUpdateRequest, animalId: string): Observable<AnimalResponse> {
    return this.http.put<AnimalResponse>(`${this.apiUrl}/${animalId}`, updatedAnimal);
  }

  deleteAnimal(animalId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${animalId}`);
  }
}
