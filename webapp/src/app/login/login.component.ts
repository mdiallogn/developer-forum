import {Component, OnInit} from '@angular/core';
import {JwtClientService} from "../services/jwt-client.service";
import {FormBuilder, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from 'ngx-toastr';
import {Router} from "@angular/router";
import {Global} from "../global-classes/global";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: any;
  token: string = "";
  baseUrl: string = "http://127.0.0.1:8000/api/v1/users";

  constructor(private jwtClientService: JwtClientService,
              private fb: FormBuilder,
              private http: HttpClient,
              private router: Router,
              private toastr: ToastrService) {
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  public login() {
    console.log("state => ", JwtClientService.isAuth)
    if (JwtClientService.isAuth) {
      this.router.navigate(["/home"])
      return;
    }
    const authRequest = {
      userName: this.loginForm.value["username"],
      password: this.loginForm.value["password"]
    };
    // const response$ = this.jwtClientService.generateToken(authRequest);
    this.http.post(this.baseUrl + "/login", authRequest, {responseType: "text" as 'json'})
      .subscribe(
        data => {
          console.warn("Token " + data);
          Global.TOKEN = data;
          this.router.navigate(["/"]);
          this.jwtClientService.connect(data.toString())
          this.toastr.success("Bon retour parmis nous " + authRequest.userName + " !")
        },
        error => {
          console.log("There is an error occurred: " + error)
          this.toastr.error("Identifiant ou mot de passe invalide !")
        }
      );
  }

  get username() {
    return this.loginForm.get('username');
  }

  get password() {
    return this.loginForm.get('password');
  }

}
