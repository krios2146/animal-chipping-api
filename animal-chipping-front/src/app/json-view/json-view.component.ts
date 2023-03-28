import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-json-view',
  templateUrl: './json-view.component.html',
  styleUrls: ['./json-view.component.css']
})
export class JsonViewComponent {
  @Input() object: any;
}
