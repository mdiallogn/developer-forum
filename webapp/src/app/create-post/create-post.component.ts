import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { JwtClientService } from '../services/jwt-client.service';
import API from '../api';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

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
        subject: e.value.subject,
        content: e.value.content
      };
      //submit data
      this.http.post(API.base + '/posts/'
        + this.jwtClientService.getUserInfo().id, data).subscribe(response => {
        this.toastr.success("Votre crée et mis en ligne avec succès");
        this.router.navigate(['/home'])
      },
      error => {
        this.toastr.error("Ce nom d'utilisateur n'est pas disponible.");
      })
    } else {
      this.toastr.error("Veuillez remplir correctement tous les champs", "Valeurs manquantes ou invalides", {timeOut: 5000});
    }
    console.log(e.form.status)
  }

}
