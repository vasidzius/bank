import {Component, Input, OnInit} from '@angular/core';
import {Account} from "../model/account";

/**
 * @title Table dynamically changing the columns displayed
 */
@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent {

  @Input()
  accounts: Account[];

  constructor() { }

  definedColumns = ['id', 'balance'];
  columnsToDisplay = this.definedColumns.slice();

  addColumn() {
    const randomColumn = Math.floor(Math.random() * this.definedColumns.length);
    this.columnsToDisplay.push(this.definedColumns[randomColumn]);
  }

  removeColumn() {
    if (this.columnsToDisplay.length) {
      this.columnsToDisplay.pop();
    }
  }

  shuffle() {
    let currentIndex = this.columnsToDisplay.length;
    while (0 !== currentIndex) {
      let randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex -= 1;

      // Swap
      let temp = this.columnsToDisplay[currentIndex];
      this.columnsToDisplay[currentIndex] = this.columnsToDisplay[randomIndex];
      this.columnsToDisplay[randomIndex] = temp;
    }
  }

}

