import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnimalTypeService } from '../service/animal-type.service';
import { AnimalTypeResponse } from '../model/animal-type/animal-type-response';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-animal-type',
  templateUrl: './animal-type.component.html',
  styleUrls: ['./animal-type.component.css']
})
export class AnimalTypeComponent {
  findForm: FormGroup;

  foundedAnimalType: AnimalTypeResponse | undefined;

  constructor(private formBuilder: FormBuilder, private animalTypeService: AnimalTypeService) {
    this.findForm = this.formBuilder.group({
      typeId: [null, Validators.required]
    })
  }

  findAnimalType() {
    const typeId = this.findForm.get('typeId')!.value;
    this.animalTypeService.getAnimalTypeById(typeId).subscribe(
      (response: AnimalTypeResponse) => {
        this.foundedAnimalType = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
