import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { AppRoutingModule } from './app-routing.module';
import { AnimalRegistrationComponent } from './animal-registration/animal-registration.component';
import { RegistrationComponent } from './registration/registration.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LocationSearchComponent } from './location-search/location-search.component';
import { TableLocationViewComponent } from './table-location-view/table-location-view.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    AnimalRegistrationComponent,
    RegistrationComponent,
    LocationSearchComponent,
    TableLocationViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
