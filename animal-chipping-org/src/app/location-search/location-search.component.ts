import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LocationSearchService } from '../location-search.service';
import { VisitedLocation } from '../visited-location/visited-location';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-location-search',
  templateUrl: './location-search.component.html',
  styleUrls: ['./location-search.component.css']
})
export class LocationSearchComponent {
  locationSearchForm: FormGroup;

  foundedLocations: VisitedLocation[] | undefined;

  constructor(private formBuilder: FormBuilder, private locationSearchService: LocationSearchService) {
    this.locationSearchForm = this.formBuilder.group({
      animalId: [null, [Validators.required, Validators.min(1)]],
      startDateTime: [null, [Validators.required]],
      endDateTime: [null, [Validators.required]],
    });
  }

  search() {
    const animalId = this.locationSearchForm.get('animalId')?.value;
    const startDate = this.locationSearchForm.get('startDateTime')?.value;
    const endDate = this.locationSearchForm.get('endDateTime')?.value;

    const startDateTime = this.convertToISODate(startDate);
    const endDateTime = this.convertToISODate(endDate);

    this.locationSearchService.search(animalId, startDateTime, endDateTime).subscribe(
      (response: VisitedLocation[]) => {
        response.forEach(location => {
          location.dateTimeOfVisitLocationPoint = this.convertFromISODate(location.dateTimeOfVisitLocationPoint);
        });
        this.foundedLocations = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private convertToISODate(dateString: string): string {
    const date = new Date(dateString);
    const isoDate = date.toISOString();
    return isoDate;
  }

  private convertFromISODate(dateString: string): string {
    const date = new Date(dateString);
    const year = date.getFullYear().toString().padStart(4, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
  }  
}
