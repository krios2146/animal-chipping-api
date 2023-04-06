import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { AnimalRegistrationService } from '../animal-registration.service';
import { AnimalResponse } from '../animal/animal-response';
import { Gender } from '../animal/enum/gender.enum';
import { AnimalCreateRequest } from '../animal/animal-create-request';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-animal-registration',
  templateUrl: './animal-registration.component.html',
  styleUrls: ['./animal-registration.component.css']
})
export class AnimalRegistrationComponent {
  registerAnimalForm: FormGroup;

  registeredAnimal: AnimalResponse | undefined;

  constructor(private formBuilder: FormBuilder, private animalRegistrationService: AnimalRegistrationService) {
    this.registerAnimalForm = this.formBuilder.group({
      weight: [null, [Validators.required, Validators.min(1)]],
      gender: [null, [Validators.required, this.enumValidator(Gender)]],
    });
  }

  registerAnimal() {
    const animal: AnimalCreateRequest = {
      weight: this.registerAnimalForm.get('weight')?.value,
      gender: this.registerAnimalForm.get('gender')?.value,
      chipperId: 1,
      length : 10,
      height : 10,
      chippingLocationId : 1,
      animalTypes: [1]
    };

    this.animalRegistrationService.registerAnimal(animal).subscribe(
      (response: AnimalResponse) => {
        this.registeredAnimal = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private enumValidator(enumRef: any): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const value = control.value;
      const isValid = Object.values(enumRef).includes(value);
      return isValid ? null : { enum: { invalidValue: value } };
    };
  }
}
