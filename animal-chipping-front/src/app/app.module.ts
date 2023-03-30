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
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { LocationViewComponent } from './location-view/location-view.component';
import { AccountViewComponent } from './account-view/account-view.component';
import { VisitedLocationViewComponent } from './visited-location-view/visited-location-view.component';
import { AnimalTypeViewComponent } from './animal-type-view/animal-type-view.component';
import { AnimalViewComponent } from './animal-view/animal-view.component';
import { JsonViewComponent } from './json-view/json-view.component';
import { AuthInterceptor } from './auth.interceptor';

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
    VisitedLocationViewComponent,
    AnimalTypeViewComponent,
    AnimalViewComponent,
    JsonViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, 
      useClass: AuthInterceptor, 
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
