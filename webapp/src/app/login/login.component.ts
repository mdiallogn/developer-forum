import {Component, OnInit} from '@angular/core';
import {JwtClientService} from "../services/jwt-client.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  token: string = "";
  authRequest: any = {
    "userName": "kasmi",
    "password": "kasmi1997"
  };

  constructor(private jwtClientService: JwtClientService) {
  }

  ngOnInit(): void {
    this.getAccessToken(this.authRequest);
  }

  public getAccessToken(authRequest: any) {
    const response$ = this.jwtClientService.generateToken(authRequest);
    // @ts-ignore
    response$.subscribe(data => {
      console.log("Token " + data);
      this.token = data;
    });
  }

}
