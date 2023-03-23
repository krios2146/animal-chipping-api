import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LocationService } from '../service/location.service';
import { LocationRequest } from '../model/location/location-request';
import { LocationResponse } from '../model/location/location-response';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent {
  locationFindForm: FormGroup;
  locationAddForm: FormGroup;

  foundedLocation: LocationResponse | undefined;
  createdLocation: LocationResponse | undefined;

  constructor(private formBuilder: FormBuilder, private locationService: LocationService) {
    this.locationFindForm = this.formBuilder.group({
      locationId: [null, [Validators.required, Validators.min(1)]],
    });

    this.locationAddForm = this.formBuilder.group({
      latitude: [null, Validators.required],
      longitude: [null, Validators.required]
    });
  }

  findLocation() {
    const locationId = this.locationFindForm.get('locationId')!.value;
    console.log(locationId);
    this.locationService.getLocationById(locationId).subscribe(
      (response: LocationResponse) => {
        this.foundedLocation = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  addLocation() {
    const request: LocationRequest = this.getRequestFromForm(this.locationAddForm);
    this.locationService.createLocation(request).subscribe(
      (response: LocationResponse) => {
        this.createdLocation = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private getRequestFromForm(locationAddForm: FormGroup): LocationRequest {
    const request: LocationRequest = {
      latitude: locationAddForm.get('latitude')?.value,
      longitude: locationAddForm.get('longitude')?.value
    };
    return request;
  }

}
