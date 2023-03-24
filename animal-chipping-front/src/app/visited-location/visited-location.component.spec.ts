import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitedLocationComponent } from './visited-location.component';

describe('VisitedLocationComponent', () => {
  let component: VisitedLocationComponent;
  let fixture: ComponentFixture<VisitedLocationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisitedLocationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisitedLocationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
