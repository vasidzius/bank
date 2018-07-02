import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountListComponent } from './account-list.component';
import {MatButtonModule, MatInputModule, MatTableModule} from "@angular/material";

describe('AccountListComponent', () => {
  let component: AccountListComponent;
  let fixture: ComponentFixture<AccountListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        MatTableModule,
        MatButtonModule,
        MatInputModule,
      ],
      declarations: [ AccountListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
