import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  baseUrl: string = "http://localhost:8000/api/users";

  constructor(private http: HttpClient) {
  }

  // @ts-ignore
  public generateToken(request: any) {
    this.http.post<string>(this.baseUrl + "/login", request, {responseType: 'text' as 'json'});
  }

  public welcome(token: string) {
    const tokenStr = 'Bearer ' + token;
    // get list of users when user is authenticated...
    const headers = new HttpHeaders();
    headers.set('Authorization', tokenStr);
    return this.http.get(this.baseUrl + "/all", {headers, responseType: "text" as 'json'});
  }
}
