import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnimalTypeComponent } from './animal-type.component';

describe('AnimalTypeComponent', () => {
  let component: AnimalTypeComponent;
  let fixture: ComponentFixture<AnimalTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AnimalTypeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnimalTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
