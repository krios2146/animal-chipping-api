import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnimalService } from '../service/animal.service';
import { AnimalRequest } from '../model/animal/animal-request';
import { AnimalResponse } from '../model/animal/animal-response';
import { HttpErrorResponse } from '@angular/common/http';
import { AnimalCreateRequest } from '../model/animal/animal-create-request';
import { AnimalUpdateRequest } from '../model/animal/animal-update-request';

@Component({
  selector: 'app-animal',
  templateUrl: './animal.component.html',
  styleUrls: ['./animal.component.css']
})
export class AnimalComponent {
  findForm: FormGroup;
  addForm: FormGroup;
  updateForm: FormGroup;
  deleteForm: FormGroup;

  foundedAnimal: AnimalResponse | undefined;
  addedAnimal: AnimalResponse | undefined;
  updatedAnimal: AnimalResponse | undefined;
  isAnimalDeleted: boolean = false;

  constructor(private formBuilder: FormBuilder, private animalService: AnimalService) {
    this.findForm = this.formBuilder.group({
      animalId: [null, [Validators.required]]
    });

    this.addForm = this.formBuilder.group({
      weight: [null, [Validators.required]],
      length: [null, [Validators.required]],
      height: [null, [Validators.required]],
      gender: [null, [Validators.required]],
      chipperId: [null, [Validators.required]],
      chippingLocationId: [null, [Validators.required]],
      animalTypes: [null, [Validators.required]]
    });

    this.updateForm = this.formBuilder.group({
      animalId: [null, [Validators.required]],
      weight: [null, [Validators.required]],
      length: [null, [Validators.required]],
      height: [null, [Validators.required]],
      gender: [null, [Validators.required]],
      chipperId: [null, [Validators.required]],
      chippingLocationId: [null, [Validators.required]],
      lifeStatus: [null, [Validators.required]]
    });

    this.deleteForm = this.formBuilder.group({
      animalId: [null, [Validators.required]]
    });
  }

  findAnimal() {
    const animalId = this.findForm.get('animalId')!.value;
    this.animalService.getAnimalById(animalId).subscribe(
      (response: AnimalResponse) => {
        this.foundedAnimal = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  addAnimal() {
    const request: AnimalRequest = this.getRequestFromForm(this.addForm);
    const addRequest: AnimalCreateRequest = {
      ...request,
      animalTypes: this.addForm.get('animalTypes')?.value
    }
    this.animalService.createAnimal(addRequest).subscribe(
      (response: AnimalResponse) => {
        this.addedAnimal = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  updateAnimal() {
    const animalId = this.updateForm.get('animalId')!.value;
    const request: AnimalRequest = this.getRequestFromForm(this.updateForm);
    const updateRequest: AnimalUpdateRequest = {
      ...request,
      lifeStatus: this.updateForm.get('lifeStatus')?.value
    }
    this.animalService.updateAnimal(updateRequest, animalId).subscribe(
      (response: AnimalResponse) => {
        this.updatedAnimal = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  deleteAnimal() {
    const animalId = this.deleteForm.get('animalId')!.value;
    this.animalService.deleteAnimal(animalId).subscribe(
      () => {
        this.isAnimalDeleted = true;
        setTimeout(
          () => {
            this.isAnimalDeleted = false;
          },
          3000
        );
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private getRequestFromForm(animalform: FormGroup): AnimalRequest {
    return {
      weight : animalform.get('weight')?.value,
      length : animalform.get('length')?.value,
      height : animalform.get('height')?.value,
      gender : animalform.get('gender')?.value,
      chipperId : animalform.get('chipperId')?.value,
      chippingLocationId : animalform.get('chippingLocationId')?.value
    };
  }
}
