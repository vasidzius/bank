import {Component, Input, OnInit} from '@angular/core';
import {Account} from "../model/account";

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  @Input()
  accounts: Account[];

  constructor() { }

  ngOnInit() {
  }

}
