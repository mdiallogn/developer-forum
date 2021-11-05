import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JwtClientService {

  baseUrl: string = "http://127.0.0.1:8000/api/users";

  constructor(private http: HttpClient) {
  }

  // @ts-ignore
  public generateToken(request: any):Observable<string> {
    this.http.post<Observable<string>>(this.baseUrl + "/login", request);
  }

  public welcome(token: string) {
    const tokenStr = 'Bearer ' + token;
    // get list of users when user is authenticated...
    const headers = new HttpHeaders();
    headers.set('Authorization', tokenStr);
    return this.http.get(this.baseUrl + "/all", {headers, responseType: "text" as 'json'});
  }
}
