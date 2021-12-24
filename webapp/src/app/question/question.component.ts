import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import API from '../api';
import { Post } from '../model/post';
import { JwtClientService } from '../services/jwt-client.service';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent {
  @Input() post: Post = new Post()
  currentUserUpVote: boolean = false
  currentUserDownVote: boolean = false
  total : number = 0
  constructor(private http: HttpClient, private jwtClient: JwtClientService, private router: Router) {
  }
  ngAfterContentChecked() {
    this.updateCurrentUserVote()
  }
  updateCurrentUserVote(): any {
    if (this.jwtClient.isAuth()) {
      const currentUser = this.jwtClient.getUserInfo()
      let arr =  this.post.upVoters.filter(v => v.id == currentUser['id'])
      this.currentUserUpVote = arr.length > 0
  
      arr =  this.post.downVoters.filter(v => v.id == currentUser['id'])
      this.currentUserDownVote = arr.length > 0
      this.total = this.post.upVoters.length - this.post.downVoters.length
    }
  }
  vote(isUpvote: boolean) : void {
    if (!this.jwtClient.isAuth()) {
      this.router.navigateByUrl('/login')
    }
    const user = this.jwtClient.getUserInfo()
    this.http.post<Post>(API.base + '/posts/' + this.post.id 
    + (isUpvote ? '/upvotes': '/downvotes'), user).subscribe(response => {
      this.post = response
      this.updateCurrentUserVote()
    })
  }

  upVote(e : Event) : void {
    e.preventDefault()
    this.vote(true)
  }
  
  downVote(e: Event) : void {
    e.preventDefault()
    this.vote(false)
  }
}
