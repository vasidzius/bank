import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Todo} from "../model/todo";

@Component({
  selector: 'app-todo-list-item',
  templateUrl: './todo-list-item.component.html',
  styleUrls: ['./todo-list-item.component.css']
})
export class TodoListItemComponent {

  @Input() todo: Todo;

  @Output()
  remove: EventEmitter<Todo> = new EventEmitter<Todo>();

  @Output()
  toggleComplete: EventEmitter<Todo> = new EventEmitter<Todo>();

  constructor() { }

  toggleTodoComplete(todo: Todo){
    this.toggleComplete.emit(todo);
  }

  removeTodo(todo: Todo){
    this.remove.emit(todo);
  }

}
