import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { NgForm } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  constructor(private toastr: ToastrService, private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
  }

  submit(e:NgForm) {
    if (e.form.status === "VALID") {
      if (e.value.password !==  e.value.rePassword) {
       this.toastr.error("Mot de passe différent");
      } else {
        const data = {
          firstName: e.value.firstName,
          lastName: e.value.lastName,
          userName: e.value.pseudo,
          password: e.value.password,
          role: 'USER'
        };
        //submit data
        this.http.post("http://127.0.0.1:8000/api/users", data).subscribe(response => {
          this.toastr.success("Compte crée avec succès");
          setTimeout(()=>{}, 2000);
          this.router.navigate(['/login'])
        })
      }

    } else {
      this.toastr.error("Veuillez remplir correctement tous les champs", "Valeurs manquantes ou invalides", {timeOut: 5000});
    }
    console.log(e.form.status)
  }

}
