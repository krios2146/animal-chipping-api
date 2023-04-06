import { Component, Input, Output } from '@angular/core';
import { VisitedLocation } from '../visited-location/visited-location';

@Component({
  selector: 'app-table-location-view',
  templateUrl: './table-location-view.component.html',
  styleUrls: ['./table-location-view.component.css']
})
export class TableLocationViewComponent {
  @Input() locations: VisitedLocation[] | undefined;
}
