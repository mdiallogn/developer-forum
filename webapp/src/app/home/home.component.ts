import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";
import { Post } from '../model/post';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  posts:Post[] = []
  baseUrl: string = "http://127.0.0.1:8000/api/v1/posts";

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    this.http.get<Post[]>(this.baseUrl).subscribe(
      data => {
        this.posts = data
      },
      error => console.log("Error occurred while gettings posts.")
    );
  }
}
