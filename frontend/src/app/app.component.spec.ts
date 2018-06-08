import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { TodoListHeaderComponent} from "./todo-list-header/todo-list-header.component";
import { FormsModule } from '@angular/forms';
import {Todo} from "./todo";
import {TodoListComponent} from "./todo-list/todo-list.component";
import {TodoListItemComponent} from "./todo-list-item/todo-list-item.component";
import {TodoListFooterComponent} from "./todo-list-footer/todo-list-footer.component";
import {TodoDataService} from "./todo-data.service";

describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule
      ],
      declarations: [
        AppComponent,
        TodoListHeaderComponent,
        TodoListComponent,
        TodoListItemComponent,
        TodoListFooterComponent
      ],
      providers: [
        TodoDataService
      ]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
