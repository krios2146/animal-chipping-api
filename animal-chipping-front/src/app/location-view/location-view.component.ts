import { Component, Input } from '@angular/core';
import { LocationResponse } from '../model/location/location-response';

@Component({
  selector: 'app-location-view',
  templateUrl: './location-view.component.html',
  styleUrls: ['./location-view.component.css']
})
export class LocationViewComponent {
  @Input() location: LocationResponse | undefined;
  @Input() isLocationDeleted: boolean = false;
}
