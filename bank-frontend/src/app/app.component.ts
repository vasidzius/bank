import {Component} from '@angular/core';
import {AccountService} from "./api/account.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  transfers = this.accountService.findUsingGET(1).toString();

  title = 'app';

  constructor(private accountService: AccountService) {
  }
}
