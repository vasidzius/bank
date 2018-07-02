import {Component, EventEmitter, Output} from '@angular/core';
import {ValueGeneratorService} from "../api/services/value-generator.service";
import {ValueGeneratorRequest} from "../model/valueGeneratorRequest";
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-value-generator',
  templateUrl: './value-generator.component.html',
  styleUrls: ['./value-generator.component.css']
})
export class ValueGeneratorComponent {

  constructor() { }

  valueGeneratorRequest : ValueGeneratorRequest = new ValueGeneratorRequest();

  @Output()
  generate : EventEmitter<ValueGeneratorRequest> = new EventEmitter();

  accountsNumberControl: FormControl;

  OnGenerateRandomValues() {
    this.generate.emit(this.valueGeneratorRequest);
    this.valueGeneratorRequest = new ValueGeneratorRequest();
  }

  OnSubmit() {
    this.accountsNumberControl = new FormControl('', [
      Validators.min(10),
    ]);

    if(!this.accountsNumberControl.valid) {
      this.generate.emit(this.valueGeneratorRequest);
      this.valueGeneratorRequest = new ValueGeneratorRequest();
    }
  }

}
