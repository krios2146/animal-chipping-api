import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AccountComponent } from './account/account.component';
import { AppRoutingModule } from './app-routing.module';
import { AnimalComponent } from './animal/animal.component';
import { AnimalTypeComponent } from './animal-type/animal-type.component';
import { LocationComponent } from './location/location.component';
import { VisitedLocationComponent } from './visited-location/visited-location.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { LocationViewComponent } from './location-view/location-view.component';
import { AccountViewComponent } from './account-view/account-view.component';
import { VisitedLocationViewComponent } from './visited-location-view/visited-location-view.component';

@NgModule({
  declarations: [
    AppComponent,
    AccountComponent,
    AnimalComponent,
    AnimalTypeComponent,
    LocationComponent,
    VisitedLocationComponent,
    LocationViewComponent,
    AccountViewComponent,
    VisitedLocationViewComponent
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
