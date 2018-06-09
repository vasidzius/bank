import { Injectable } from '@angular/core';
import {Todo} from "./model/todo";
import {catchError} from "rxjs/operators";
import {throwError} from "rxjs/index";
import {Observable} from "rxjs/internal/Observable";

@Injectable({
  providedIn: 'root'
})
export class ApiMockService {

  constructor() { }

  public getAllTodos(): Observable<Todo[]> {
    return Observable.create([
      new Todo({id: 1, title: "Read article", complete: false})
    ])
  }

  public getTodoById( todoId: number ): Observable<Todo> {
    return Observable.create(new Todo({id: 1, title: "Read article", complete: false})
    )
  }

  public createTodo( todo: Todo ): Observable<Todo> {
    return Observable.create(new Todo({id: 1, title: "Read article", complete: false})
    )
  }

  public updateTodo( todo: Todo ) {
      return Observable.create(new Todo({id: 1, title: "Read article", complete: false})
      );
  }

  public deleteTodoById( todoId: number ) : Observable<Todo> {
    return null
  }

  private handleError( error: Response | any ) {
    console.error('ApiService::handleError', error);
    return throwError(error);
  };
}
