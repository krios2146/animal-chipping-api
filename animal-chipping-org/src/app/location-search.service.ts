import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { VisitedLocation } from './visited-location/visited-location';

@Injectable({
  providedIn: 'root'
})
export class LocationSearchService {
  private apiUrl = 'http://78.153.130.215:8080/animals';
  private email: string = "root@email.com";
  private password: string = "root";

  constructor(private http: HttpClient) { }

  search(animalId: number, startDateTime: string, endDateTime: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Authorization': 'Basic ' + btoa(`${this.email}:${this.password}`)
      })
    };

    return this.http.get<VisitedLocation[]>(`${this.apiUrl}/${animalId}/locations?startDateTime=${startDateTime}&endDateTime=${endDateTime}`, httpOptions);
  }
}
