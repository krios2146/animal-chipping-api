import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LocationResponse } from '../model/location/location-response';
import { LocationRequest } from '../model/location/location-request';

@Injectable({
    providedIn: 'root',
})
export class AnimalService {
    private apiUrl = 'http://localhost:8080/locatinos';

    constructor(private http: HttpClient) { }

    getLocationById(locationId: number): Observable<LocationResponse> {
        return this.http.get<LocationResponse>(`${this.apiUrl}/${locationId}`);
    }

    createLocation(location: LocationRequest): Observable<LocationResponse> {
        return this.http.post<LocationResponse>(`${this.apiUrl}`, location);
    }

    updateLocation(updatedLocation: LocationRequest, locationId: string): Observable<LocationResponse> {
        return this.http.put<LocationResponse>(`${this.apiUrl}/${locationId}`, updatedLocation);
    }

    deleteLocation(locationId: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${locationId}`);
    }
}
