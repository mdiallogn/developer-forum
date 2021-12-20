import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import API from '../api';
import { Post } from '../model/post';

@Component({
  selector: 'app-post-details',
  templateUrl: './post-details.component.html',
  styleUrls: ['./post-details.component.css']
})
export class PostDetailsComponent implements OnInit {
  post: Post = new Post()
  constructor(private http: HttpClient,
              private router: ActivatedRoute) { }

  ngOnInit(): void {
    this.router.params.subscribe(params => {
      const id = params['id']
      this.http.get<Post>(API.base + '/posts/' + id).subscribe(response => {
        this.post = response
      })
    })
  }

  refresh(newPost: Post): void {
    this.post = newPost
  }
}
