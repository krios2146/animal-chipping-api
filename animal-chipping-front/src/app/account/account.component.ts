import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AccountResponse } from '../model/account/account-response';
import { AccountService } from '../service/account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AccountRequest } from '../model/account/account-request';

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent {
  findForm: FormGroup;
  addForm: FormGroup;
  updateForm: FormGroup;
  deleteForm: FormGroup;

  activeTab: string | undefined;

  foundedAccount: AccountResponse | undefined;
  addedAccount: AccountResponse | undefined;
  updatedAccount: AccountResponse | undefined;
  isAccountDeleted: boolean = false;

  constructor(private formBuilder: FormBuilder, private accountService: AccountService) {
    this.findForm = this.formBuilder.group({
      accountId: [null, [Validators.required, Validators.min(1)]],
    });

    this.addForm = this.formBuilder.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required]
    });

    this.updateForm = this.formBuilder.group({
      accountId: [null, [Validators.required, Validators.min(1)]],
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      email: [null, [Validators.required, Validators.email]],
      password: [null, Validators.required]
    });

    this.deleteForm = this.formBuilder.group({
      accountId: [null, [Validators.required, Validators.min(1)]],
    });
  }

  findAccount() {
    const accountId = this.findForm.get('accountId')!.value;
    this.accountService.getAccountById(accountId).subscribe(
      (response: AccountResponse) => {
        this.foundedAccount = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    )
  }

  addAccount() {
    this.accountService.createAccount(this.addForm.value).subscribe(
      (response: AccountResponse) => {
        this.addedAccount = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  updateAccount() {
    const accountId = this.updateForm.get('accountId')!.value;
    const request: AccountRequest = this.getRequestFromForm(this.updateForm);
    this.accountService.updateAccount(accountId, request).subscribe(
      (response: AccountResponse) => {
        this.updatedAccount = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  deleteAccount() {
    const accountId = this.deleteForm.get('accountId')!.value;
    this.accountService.deleteAccount(accountId).subscribe(
      () => {
        this.isAccountDeleted = true;
        setTimeout(
          () => {
            this.isAccountDeleted = false;
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

  private getRequestFromForm(locationForm: FormGroup): AccountRequest {
    return {
      firstName: locationForm.get('firstName')?.value,
      lastName: locationForm.get('lastName')?.value,
      email: locationForm.get('email')?.value,
      password: locationForm.get('password')?.value
    };
  }
}
