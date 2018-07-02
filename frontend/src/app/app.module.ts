import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {TodoListHeaderComponent} from './todo-list-header/todo-list-header.component';
import {TodoListComponent} from './todo-list/todo-list.component';
import {TodoListItemComponent} from './todo-list-item/todo-list-item.component';
import {TodoListFooterComponent} from './todo-list-footer/todo-list-footer.component';
import {TodoDataService} from "./api/services/todo-data.service";
import { AccountListComponent } from './account-list/account-list.component';
import {AccountService} from "./api/services/account.service";
import {MatButtonModule, MatInputModule, MatTableModule} from "@angular/material";
import { TransferListComponent } from './transfer-list/transfer-list.component';
import {TransferService} from "./api/services/transfer.service";
import {ValueGeneratorService} from "./api/services/value-generator.service";
import { ValueGeneratorComponent } from './value-generator/value-generator.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    TodoListHeaderComponent,
    TodoListComponent,
    TodoListItemComponent,
    TodoListFooterComponent,
    AccountListComponent,
    TransferListComponent,
    ValueGeneratorComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatTableModule,
    MatButtonModule,
    MatInputModule,
    BrowserAnimationsModule,
    ReactiveFormsModule
  ],
  providers: [ TodoDataService, AccountService, TransferService, ValueGeneratorService ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
}
