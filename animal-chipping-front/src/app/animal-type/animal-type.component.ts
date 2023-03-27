import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AnimalTypeService } from '../service/animal-type.service';
import { AnimalTypeResponse } from '../model/animal-type/animal-type-response';
import { HttpErrorResponse } from '@angular/common/http';
import { AnimalTypeRequest } from '../model/animal-type/animal-type-request';

@Component({
  selector: 'app-animal-type',
  templateUrl: './animal-type.component.html',
  styleUrls: ['./animal-type.component.css']
})
export class AnimalTypeComponent {
  findForm: FormGroup;
  addForm: FormGroup;
  updateForm: FormGroup;
  deleteForm: FormGroup;

  activeTab: string | undefined;

  foundedAnimalType: AnimalTypeResponse | undefined;
  addedAnimalType: AnimalTypeResponse | undefined;
  updatedAnimalType: AnimalTypeResponse | undefined;
  isAnimalTypeDeleted: boolean = false;

  constructor(private formBuilder: FormBuilder, private animalTypeService: AnimalTypeService) {
    this.findForm = this.formBuilder.group({
      typeId: [null, [Validators.required, Validators.min(1)]]
    });

    this.addForm = this.formBuilder.group({
      type: [null, Validators.required]
    });

    this.updateForm = this.formBuilder.group({
      typeId: [null, [Validators.required, Validators.min(1)]],
      type: [null, Validators.required]
    });

    this.deleteForm = this.formBuilder.group({
      typeId: [null, [Validators.required, Validators.min(1)]]
    });
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

  addAnimalType() {
    const request: AnimalTypeRequest = this.getRequestFromForm(this.addForm);
    this.animalTypeService.createAnimalType(request).subscribe(
      (response: AnimalTypeResponse) => {
        this.addedAnimalType = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  updateAnimalType() {
    const typeId = this.updateForm.get('typeId')!.value;
    const request: AnimalTypeRequest = this.getRequestFromForm(this.updateForm);
    this.animalTypeService.updateAnimalType(request, typeId).subscribe(
      (response: AnimalTypeResponse) => {
        this.updatedAnimalType = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  deleteAnimalType() {
    const typeId = this.deleteForm.get('typeId')!.value;
    this.animalTypeService.deleteAnimalType(typeId).subscribe(
      () => {
        this.isAnimalTypeDeleted = true;
        setTimeout(
          () => {
            this.isAnimalTypeDeleted = false;
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

  private getRequestFromForm(animalTypeForm: FormGroup): AnimalTypeRequest {
    return {
      type: animalTypeForm.get('type')?.value
    };
  }
}
