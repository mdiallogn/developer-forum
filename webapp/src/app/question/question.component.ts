import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../model/post';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent {
  @Input() post: Post = new Post()
  constructor() {
  }
}
