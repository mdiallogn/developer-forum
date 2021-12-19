import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import API from '../api';
import { Post } from '../model/post';
import { JwtClientService } from '../services/jwt-client.service';
import { Comment } from '../model/comment'

@Component({
  selector: 'app-reply',
  templateUrl: './reply.component.html',
  styleUrls: ['./reply.component.css']
})
export class ReplyComponent implements OnInit {

  @Input() post: Post = new Post()
  @Input() comment: Comment = new Comment()

  constructor(private toastr: ToastrService,
    private http: HttpClient,
    private router: Router,
    private jwtClientService: JwtClientService) { }

    ngOnInit(): void {
    }

    submit(e:NgForm) {
    console.log('form validated', e)
    if (e.form.status === "VALID") {
      const data = {
      message: e.value.message,
      author: this.jwtClientService.getUserInfo()
    };
    //submit data
    this.http.post(API.base + '/posts/' + this.post.id + '/comments/' + this.comment.id + '/replies', data).subscribe(response => {
      this.toastr.success("Commentaire crée avec succès !");
      e.reset()
    },
    error => {
      this.toastr.error("L'envoi du commentaire à échouer!");
    })
    } else {
      this.toastr.error("Veuillez remplir correctement tous les champs", "Valeurs manquantes ou invalides", {timeOut: 5000});
    }
    console.log(e.form.status)
}
}
