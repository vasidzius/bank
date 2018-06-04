import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';


import {AppComponent} from './app.component';
import {AccountcontrollerApi} from "./api/AccountcontrollerApi";
import {TransfercontrollerApi} from "./api/TransfercontrollerApi";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [AccountcontrollerApi, TransfercontrollerApi],
  bootstrap: [AppComponent]
})
export class AppModule { }
