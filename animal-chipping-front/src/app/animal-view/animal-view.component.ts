import { Component, Input } from '@angular/core';
import { AnimalResponse } from '../model/animal/animal-response';

@Component({
  selector: 'app-animal-view',
  templateUrl: './animal-view.component.html',
  styleUrls: ['./animal-view.component.css']
})
export class AnimalViewComponent {
  @Input() animal: AnimalResponse | undefined;
  @Input() isAnimalDeleted: boolean = false;
}
