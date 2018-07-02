import {TestBed, async} from '@angular/core/testing';
import {AppComponent} from './app.component';
import {TodoListHeaderComponent} from "./todo-list-header/todo-list-header.component";
import {FormsModule} from '@angular/forms';
import {Todo} from "./model/todo";
import {TodoListComponent} from "./todo-list/todo-list.component";
import {TodoListItemComponent} from "./todo-list-item/todo-list-item.component";
import {TodoListFooterComponent} from "./todo-list-footer/todo-list-footer.component";
import {TodoDataService} from "./api/services/todo-data.service";
import {HttpClient, HttpHandler} from "@angular/common/http";
import {ApiMockService} from "./api/api-mock.service";
import {ApiService} from "./api/api.service";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {AccountService} from "./api/services/account.service";
import {TransferService} from "./api/services/transfer.service";
import {ValueGeneratorService} from "./api/services/value-generator.service";
import {AccountMockService} from "./api/services/mock/account-mock.service";

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        TodoDataService,
        {
          provide: ApiService,
          useClass: ApiMockService
        },
        {
          provide: AccountService,
          useClass: AccountMockService
        },
        TransferService,
        HttpClient,
        HttpHandler,
        ValueGeneratorService
      ],
      schemas: [
        NO_ERRORS_SCHEMA
      ]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
