import {Component, EventEmitter, Output} from '@angular/core';
import {ValueGeneratorRequest} from "../model/valueGeneratorRequest";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-value-generator',
  templateUrl: './value-generator.component.html',
  styleUrls: [ './value-generator.component.css' ]
})
export class ValueGeneratorComponent {

  constructor() { }

  valueGeneratorRequest: ValueGeneratorRequest = new ValueGeneratorRequest();

  @Output()
  generate: EventEmitter<ValueGeneratorRequest> = new EventEmitter();

  minAccountsNumber = 5;
  accountsNumberControl = new FormControl("", [ Validators.min(this.minAccountsNumber) ]);

  minThreadNumberBetweenTwo = 2;
  threadNumberBetweenTwo = new FormControl("", [ Validators.min(this.minThreadNumberBetweenTwo) ]);

  minTransfersBetweenTwo = 3;
  transfersBetweenTwo = new FormControl("", [ Validators.min(this.minTransfersBetweenTwo) ]);

  minAccountsToDelete = 1;
  accountsToDelete = new FormControl("", [ Validators.min(this.minAccountsToDelete) ]);

  minTransfersIncreasing = 1;
  transfersIncreasing = new FormControl("", [ Validators.min(this.minTransfersIncreasing) ]);

  minTransfersDecreasing = 1;
  transfersDecreasing = new FormControl("", [ Validators.min(this.minTransfersDecreasing) ]);

  OnGenerateRandomValues() {
    this.generate.emit(this.valueGeneratorRequest);
    this.valueGeneratorRequest = new ValueGeneratorRequest();
  }

  OnSubmit() {
    if (this.accountsNumberControl.valid) {
      this.valueGeneratorRequest.accountsNumber = this.accountsNumberControl.value ? this.accountsNumberControl.value : this.minAccountsNumber;
      this.valueGeneratorRequest.threadNumberBetweenTwo = this.threadNumberBetweenTwo.value ? this.threadNumberBetweenTwo.value : this.minThreadNumberBetweenTwo;
      this.valueGeneratorRequest.transfersBetweenTwo = this.transfersBetweenTwo.value ? this.transfersBetweenTwo.value : this.minTransfersBetweenTwo;
      this.valueGeneratorRequest.accountsToDelete = this.accountsToDelete.value ? this.accountsToDelete.value : this.minAccountsToDelete;
      this.valueGeneratorRequest.transfersIncreasing = this.transfersIncreasing.value ? this.transfersIncreasing.value : this.minTransfersIncreasing;
      this.valueGeneratorRequest.transfersDecreasing = this.transfersDecreasing.value ? this.transfersDecreasing.value : this.minTransfersDecreasing;

      this.generate.emit(this.valueGeneratorRequest);
      this.valueGeneratorRequest = new ValueGeneratorRequest();
    }
  }

}
