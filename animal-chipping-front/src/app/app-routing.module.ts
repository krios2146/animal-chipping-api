import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountComponent } from './account/account.component';
import { AnimalComponent } from './animal/animal.component';
import { AnimalTypeComponent } from './animal-type/animal-type.component';
import { LocationComponent } from './location/location.component';

const routes: Routes = [
  { path: 'account', component: AccountComponent },
  { path: 'animal', component: AnimalComponent },
  { path: 'animal-type', component: AnimalTypeComponent },
  { path: 'location', component: LocationComponent }
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
