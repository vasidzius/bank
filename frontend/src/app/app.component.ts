import {Component, OnInit} from '@angular/core';
import {TodoDataService} from "./api/services/todo-data.service";
import {Todo} from "./model/todo";
import {Account} from "./model/account";
import {AccountService} from "./api/services/account.service";
import {Transfer} from "./model/transfer";
import {TransferService} from "./api/services/transfer.service";
import {ValueGeneratorService} from "./api/services/value-generator.service";
import {ValueGeneratorRequest} from "./model/valueGeneratorRequest";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [ './app.component.css' ],
  providers: []
})
export class AppComponent implements OnInit{

  todos: Todo[] = [];
  accounts: Account[] = [];
  transfers: Transfer[] = [];

  constructor(
    private todoDataService: TodoDataService,
    private accountService: AccountService,
    private transferService: TransferService,
    private valueGeneratorService: ValueGeneratorService
  ) {}

  ngOnInit(): void {
    this.todoDataService
      .getAllTodos()
      .subscribe(
        (todos) => {
          this.todos = todos;
        }
      )

    this.accountService
      .findAllUsingGET()
      .subscribe(
        accounts => {
          this.accounts = accounts;
        }
      )

    this.transferService
      .findAllTransfers()
      .subscribe(
        transfers => {
          this.transfers = transfers;
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

  onGenerateRandomValues( valueGeneratorRequest : ValueGeneratorRequest ) {
    this.valueGeneratorService.generate(valueGeneratorRequest)
      .subscribe();
  }
}
