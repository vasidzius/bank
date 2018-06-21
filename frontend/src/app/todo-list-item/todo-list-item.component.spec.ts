import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TodoListItemComponent} from './todo-list-item.component';
import {FormsModule} from "@angular/forms";
import {Todo} from "../model/todo";

describe('TodoListItemComponent', () => {
  let component: TodoListItemComponent;
  let fixture: ComponentFixture<TodoListItemComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ FormsModule ],
      declarations: [ TodoListItemComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TodoListItemComponent);
    component = fixture.componentInstance;
    component.todo = new Todo({ id: 1, title: 'Test', complete: false });
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
