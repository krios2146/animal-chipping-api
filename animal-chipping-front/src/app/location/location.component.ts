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
  locationAddForm: FormGroup;
  locationAddResponse: LocationResponse | undefined;

  constructor(private formBuilder: FormBuilder, private locationService: LocationService) {
    this.locationAddForm = this.formBuilder.group({
      latitude: [null, Validators.required],
      longitude: [null, Validators.required]
    });
  }

  addLocation() {
    const request : LocationRequest = this.getRequestFromForm(this.locationAddForm);
    this.locationService.createLocation(request).subscribe(
      (response: LocationResponse) => {
        this.locationAddResponse = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private getRequestFromForm(locationAddForm: FormGroup): LocationRequest {
    const request : LocationRequest = {
      latitude: locationAddForm.get('latitude')?.value,
      longitude: locationAddForm.get('longitude')?.value
    };
    return request;
  }

}
