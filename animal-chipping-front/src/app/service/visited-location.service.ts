import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VisitedLocationResponse } from '../model/visited-location/visited-location-response';
import { VisitedLocationRequest } from '../model/visited-location/visited-location-request';


@Injectable({
  providedIn: 'root'
})
export class VisitedLocationService {
  private apiUrl = 'http://localhost:8080/animals';

  constructor(private http: HttpClient) { }

  addVisitedLocationToAnimal(animalId: number, locationId: number): Observable<VisitedLocationResponse> {
    return this.http.post<VisitedLocationResponse>(`${this.apiUrl}/${animalId}/locations/${locationId}`, null);
  }

  updateVisitedLocationOfAnimal(animalId: number, updatedLocation: VisitedLocationRequest): Observable<VisitedLocationResponse> {
    return this.http.put<VisitedLocationResponse>(`${this.apiUrl}/${animalId}/locations`, updatedLocation);
  }

  deleteVisitedLocationFromAnimal(animalId: number, visitedLocationId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${animalId}/locations/${visitedLocationId}`);
  }

}
