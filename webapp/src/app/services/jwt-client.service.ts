import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import { CookieService } from 'ngx-cookie-service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  public static isAuth: boolean = false;
  public static currentToken : string = "";
  baseUrl: string = "http://127.0.0.1:8000/api/v1/users";

  constructor(private http: HttpClient,
              private cookie: CookieService) {
  }

  // @ts-ignore
  public generateToken(request: any):Observable<string> {
    this.http.post<Observable<string>>(this.baseUrl + "/login", request);
  }

  public refresh() {
    const expiration = parseInt(this.cookie.get('expiration'));
    if(expiration > Date.now()) {
      JwtClientService.isAuth = true;
      JwtClientService.currentToken = this.cookie.get('currentToken');
      this.cookie.set('expiration', (Date.now() + 1000*60*10)+'');
    } else {
      this.disconnect();
    }
    JwtClientService.isAuth = this.cookie.get('isAuth') == 'true'
  }
  public welcome(token: string) {
    const tokenStr = 'Bearer ' + token;
    // get list of users when user is authenticated...
    const headers = new HttpHeaders();
    headers.set('Authorization', tokenStr);
    return this.http.get(this.baseUrl , {headers, responseType: "text" as 'json'});
  }

  public connect(userToken: string) {
    this.cookie.set('isAuth', 'true');
    this.cookie.set('currentToken', userToken);
    this.cookie.set('expiration', (Date.now() + 1000*60*10)+'');
    JwtClientService.isAuth = true;
    JwtClientService.currentToken = userToken;
  }

  public disconnect() {
    this.cookie.delete('isAuth')
    this.cookie.delete('currentToken')
    this.cookie.delete('expiration')
    JwtClientService.isAuth = false;
    JwtClientService.currentToken = "";
  }

}
