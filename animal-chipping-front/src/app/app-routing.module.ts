import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './account/account.component';
import { AnimalComponent } from './animal/animal.component';

const routes: Routes = [
  { path: 'account', component: AccountComponent },
  { path: 'animal', component: AnimalComponent },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
