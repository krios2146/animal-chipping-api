import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalTypeViewComponent } from './animal-type-view.component';

describe('AnimalTypeViewComponent', () => {
  let component: AnimalTypeViewComponent;
  let fixture: ComponentFixture<AnimalTypeViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimalTypeViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimalTypeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
