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
  findForm: FormGroup;
  addForm: FormGroup;
  updateForm: FormGroup;
  deleteForm: FormGroup;

  activeTab: string | undefined;

  foundedLocation: LocationResponse | undefined;
  addedLocation: LocationResponse | undefined;
  updatedLocation: LocationResponse | undefined;
  isLocationDeleted: boolean = false;

  constructor(private formBuilder: FormBuilder, private locationService: LocationService) {
    this.findForm = this.formBuilder.group({
      locationId: [null, [Validators.required, Validators.min(1)]],
    });

    this.addForm = this.formBuilder.group({
      latitude: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitude: [null, [Validators.required, Validators.min(-180), Validators.max(180)]],
    });

    this.updateForm = this.formBuilder.group({
      locationId: [null, [Validators.required, Validators.min(1)]],
      latitude: [null, [Validators.required, Validators.min(-90), Validators.max(90)]],
      longitude: [null, [Validators.required, Validators.min(-180), Validators.max(180)]]
    });

    this.deleteForm = this.formBuilder.group({
      locationId: [null, [Validators.required, Validators.min(1)]],
    });
  }

  findLocation() {
    const locationId = this.findForm.get('locationId')!.value;
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
    const request: LocationRequest = this.getRequestFromForm(this.addForm);
    this.locationService.createLocation(request).subscribe(
      (response: LocationResponse) => {
        this.addedLocation = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  updateLocation() {
    const request: LocationRequest = this.getRequestFromForm(this.updateForm);
    const locationId = this.updateForm.get('locationId')!.value;
    this.locationService.updateLocation(request, locationId).subscribe(
      (response: LocationResponse) => {
        this.updatedLocation = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  deleteLocation() {
    const locationId = this.deleteForm.get('locationId')!.value;
    this.locationService.deleteLocation(locationId).subscribe(
      () => {
        this.isLocationDeleted = true;
        setTimeout(
          () => {
            this.isLocationDeleted = false;
          },
          3000
        );
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  setActiveTab(tabName: string) {
    if (this.activeTab === tabName) {
      this.activeTab = undefined;
      return;
    }

    this.activeTab = tabName;

    console.log(this.activeTab);
  }

  private getRequestFromForm(locationForm: FormGroup): LocationRequest {
    return {
      latitude: locationForm.get('latitude')?.value,
      longitude: locationForm.get('longitude')?.value
    };
  }
}
