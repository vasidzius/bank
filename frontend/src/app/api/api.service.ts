import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Todo} from "./model/todo";
import {Observable} from "rxjs/internal/Observable";
import {catchError} from "rxjs/operators";
import {throwError} from 'rxjs';

const API_URL = environment.apiUr;

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private httpClient: HttpClient
  ) {
  }

  public getAllTodos(): Observable<Todo[]> {
    return this.httpClient.get<Todo[]>(API_URL + '/todos')
      .pipe(
        catchError(this.handleError)
      );
  }

  public getTodoById( todoId: number ): Observable<Todo> {
    return this.httpClient.get<Todo>(API_URL + '/todos/' + todoId)
      .pipe(catchError(this.handleError))
  }

  public createTodo( todo: Todo ): Observable<Todo> {
    return this.httpClient.post<Todo>(API_URL + '/todos', todo)
      .pipe(catchError(this.handleError))
  }

  public updateTodo( todo: Todo ) {
    return this.httpClient.put<Todo>(API_URL + '/todos/' + todo.id, todo)
      .pipe(catchError(this.handleError))
  }

  public deleteTodoById( todoId: number ) : Observable<Todo> {
    return this.httpClient.delete<Todo>(API_URL + '/todos/' + todoId)
      .pipe(catchError(this.handleError))
  }

  private handleError( error: Response | any ) {
    console.error('ApiService::handleError', error);
    return throwError(error);
  };
}
