import {inject, TestBed} from '@angular/core/testing';
import {TodoDataService} from './todo-data.service';
import {HttpClient, HttpHandler} from "@angular/common/http";
import {ApiService} from "../api.service";
import {ApiMockService} from "../api-mock.service";

describe('TodoDataService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        TodoDataService,
        {
          provide: ApiService,
          useClass: ApiMockService
        },
        HttpClient,
        HttpHandler
      ]
    });
  });

  it('should ...', inject([ TodoDataService ], ( service: TodoDataService ) => {
    expect(service).toBeTruthy();
  }));


});
