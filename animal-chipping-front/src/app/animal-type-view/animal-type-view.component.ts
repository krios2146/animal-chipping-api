import { Component, Input } from '@angular/core';
import { AnimalTypeResponse } from '../model/animal-type/animal-type-response';

@Component({
  selector: 'app-animal-type-view',
  templateUrl: './animal-type-view.component.html',
  styleUrls: ['./animal-type-view.component.css']
})
export class AnimalTypeViewComponent {
  @Input() animalType: AnimalTypeResponse | undefined;
  @Input() isAnimalTypeDeleted: boolean = false;
}
