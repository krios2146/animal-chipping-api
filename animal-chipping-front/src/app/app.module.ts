import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AccountComponent } from './account/account.component';
import { AppRoutingModule } from './app-routing.module';
import { AnimalComponent } from './animal/animal.component';
import { AnimalTypeComponent } from './animal-type/animal-type.component';
import { LocationComponent } from './location/location.component';
import { VisitedLocationComponent } from './visited-location/visited-location.component';

@NgModule({
  declarations: [
    AppComponent,
    AccountComponent,
    AnimalComponent,
    AnimalTypeComponent,
    LocationComponent,
    VisitedLocationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
