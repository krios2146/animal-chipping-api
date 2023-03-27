import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitedLocationViewComponent } from './visited-location-view.component';

describe('VisitedLocationViewComponent', () => {
  let component: VisitedLocationViewComponent;
  let fixture: ComponentFixture<VisitedLocationViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisitedLocationViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisitedLocationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
