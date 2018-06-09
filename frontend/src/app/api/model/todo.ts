export class Todo {
  id: number;
  title: string;
  complete: boolean;


  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
