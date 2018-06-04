import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';


import {AppComponent} from './app.component';
import {AccountService} from "./api/account.service";
import {TransferService} from "./api/transfer.service";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [AccountService, TransferService],
  bootstrap: [AppComponent]
})
export class AppModule { }
