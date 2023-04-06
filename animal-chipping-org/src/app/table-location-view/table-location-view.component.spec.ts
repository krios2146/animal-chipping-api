import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableLocationViewComponent } from './table-location-view.component';

describe('TableLocationViewComponent', () => {
  let component: TableLocationViewComponent;
  let fixture: ComponentFixture<TableLocationViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TableLocationViewComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TableLocationViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
