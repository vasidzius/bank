import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferListComponent } from './transfer-list.component';
import {MatButtonModule, MatInputModule, MatTableModule} from "@angular/material";

describe('TransferListComponent', () => {
  let component: TransferListComponent;
  let fixture: ComponentFixture<TransferListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        MatTableModule,
        MatButtonModule,
        MatInputModule,
      ],
      declarations: [ TransferListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
