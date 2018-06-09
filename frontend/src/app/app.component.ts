import {Component, OnInit} from '@angular/core';
import {TodoDataService} from "./api/services/todo-data.service";
import {Todo} from "./api/model/todo";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [ './app.component.css' ],
  providers: []
})
export class AppComponent implements OnInit{

  todos: Todo[] = [];

  constructor( private todoDataService: TodoDataService ) {
  }

  ngOnInit(): void {
    this.todoDataService
      .getAllTodos()
      .subscribe(
        (todos) => {
          this.todos = todos;
        }
      )
  }

  onToggleTodoComplete( todo ) {
    this.todoDataService.toggleTodoComplete(todo)
      .subscribe( updatedTodo => {
        todo = updatedTodo;
      });
  }

  onRemoveTodo( todo ) {
    this.todoDataService.deleteTodoById(todo.id)
      .subscribe( () => {
        this.todos = this.todos.filter( t => t.id != todo.id )
      });
  }

  onAddTodo( todo: Todo ) {
    this.todoDataService.addTodo(todo)
      .subscribe(
        (newTodo) => {
          this.todos.push(newTodo);
        }
      );
  }
}
