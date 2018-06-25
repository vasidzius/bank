import {Component, Input, OnInit} from '@angular/core';
import {Transfer} from "../model/transfer";

@Component({
  selector: 'app-transfer-list',
  templateUrl: './transfer-list.component.html',
  styleUrls: ['./transfer-list.component.css']
})
export class TransferListComponent {

  @Input()
  transfers: Transfer[];

  constructor() {
  }

  definedColumns = [ 'id', 'fromAccountId', 'toAccountId', 'executed', 'amountDoubleView' ];
  columnsToDisplay = this.definedColumns.slice();

  addColumn() {
    const randomColumn = Math.floor(Math.random() * this.definedColumns.length);
    this.columnsToDisplay.push(this.definedColumns[ randomColumn ]);
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
      let temp = this.columnsToDisplay[ currentIndex ];
      this.columnsToDisplay[ currentIndex ] = this.columnsToDisplay[ randomIndex ];
      this.columnsToDisplay[ randomIndex ] = temp;
    }
  }

}
