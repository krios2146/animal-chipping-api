import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './account/account.component';
import { AnimalComponent } from './animal/animal.component';
import { AnimalTypeComponent } from './animal-type/animal-type.component';

const routes: Routes = [
  { path: 'account', component: AccountComponent },
  { path: 'animal', component: AnimalComponent },
  { path: 'animal-type', component: AnimalTypeComponent }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
