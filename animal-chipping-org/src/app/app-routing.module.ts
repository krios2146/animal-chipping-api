import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { RegistrationComponent } from './registration/registration.component';
import { AnimalRegistrationComponent } from './animal-registration/animal-registration.component';
import { LocationSearchComponent } from './location-search/location-search.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'animal-registration', component: AnimalRegistrationComponent },
  { path: 'location-search', component: LocationSearchComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
