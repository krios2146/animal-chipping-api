import { Component, Input } from '@angular/core';
import { VisitedLocationResponse } from '../model/visited-location/visited-location-response';

@Component({
  selector: 'app-visited-location-view',
  templateUrl: './visited-location-view.component.html',
  styleUrls: ['./visited-location-view.component.css']
})
export class VisitedLocationViewComponent {
  @Input() visitedLocation: VisitedLocationResponse | undefined;
  @Input() isVisitedLocationDeleted: boolean = false;
}
