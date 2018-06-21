import {Component, EventEmitter, Input, Output} from '@angular/core';
import {TodoDataService} from "../api/services/todo-data.service";
import {Todo} from "../model/todo";

@Component({
  selector: 'app-todo-list',
  templateUrl: './todo-list.component.html',
  styleUrls: ['./todo-list.component.css'],
  providers: [TodoDataService]
})
export class TodoListComponent {

  constructor() { }

  @Input()
  todos: Todo[];

  @Output()
  remove: EventEmitter<Todo> = new EventEmitter<Todo>();

  @Output()
  toggleComplete: EventEmitter<Todo> = new EventEmitter<Todo>();

  onToggleTodoComplete(todo: Todo) {
    this.toggleComplete.emit(todo);
  }

  onRemoveTodo(todo: Todo) {
    this.remove.emit(todo);
  }
}
