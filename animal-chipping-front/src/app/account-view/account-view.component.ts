import { Component, Input } from '@angular/core';
import { AccountResponse } from '../model/account/account-response';

@Component({
  selector: 'app-account-view',
  templateUrl: './account-view.component.html',
  styleUrls: ['./account-view.component.css']
})
export class AccountViewComponent {
  @Input() account: AccountResponse | undefined;
  @Input() isAccountDeleted: boolean = false;
}
