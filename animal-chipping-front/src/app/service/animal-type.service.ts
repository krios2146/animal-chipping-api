import { AnimalTypeResponse } from './../model/animal-type-response';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AnimalTypeRequest } from '../model/animal-type-request';

@Injectable({
  providedIn: 'root',
})
export class AnimalTypeService {
  private apiUrl = 'http://localhost:8080/animals/types';

  constructor(private http: HttpClient) {}

  getAnimalTypeById(typeId: number): Observable<AnimalTypeResponse> {
    return this.http.get<AnimalTypeResponse>(`${this.apiUrl}/${typeId}`);
  }

  createAnimalType(type: AnimalTypeRequest): Observable<AnimalTypeResponse> {
    return this.http.post<AnimalTypeResponse>(`${this.apiUrl}`, type);
  }

  updateAnimalType(updatedType: AnimalTypeRequest, typeId: string): Observable<AnimalTypeResponse> {
    return this.http.put<AnimalTypeResponse>(`${this.apiUrl}/${typeId}`, updatedType);
  }

  deleteAnimalType(animalId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${animalId}`);
  }
}