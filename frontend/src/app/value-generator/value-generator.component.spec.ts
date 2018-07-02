import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ValueGeneratorComponent } from './value-generator.component';

describe('ValueGeneratorComponent', () => {
  let component: ValueGeneratorComponent;
  let fixture: ComponentFixture<ValueGeneratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ValueGeneratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ValueGeneratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
