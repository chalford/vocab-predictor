import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-editor',
  templateUrl: './editor.component.html',
  styleUrls: ['./editor.component.css']
})
export class EditorComponent implements OnInit {
  @Output() textUpdated = new EventEmitter();

  text = 'hello hackathon';

  constructor() { }

  ngOnInit() {
  }

  suggest(text): void {
    this.textUpdated.emit(text);
  }


}
