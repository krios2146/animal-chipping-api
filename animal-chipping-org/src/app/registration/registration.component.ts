import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RegistrationService } from '../registration.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AccountResponse } from '../account/account-response';
import { DataShareService } from '../data-share.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  registerForm: FormGroup;

  registeredAccount: AccountResponse | undefined;

  constructor(private formBuilder: FormBuilder, private registrationService: RegistrationService, private dataShareService: DataShareService) {
    this.registerForm = this.formBuilder.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required]
    });
  }

  register() {
    this.dataShareService.setAccount(this.registerForm.value!);

    this.registrationService.register(this.registerForm.value).subscribe(
      (response: AccountResponse) => {
        this.registeredAccount = response;
        this.dataShareService.setAccountId(response.id);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
