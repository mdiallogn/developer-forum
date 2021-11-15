import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {Global} from "../global-classes/global";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  baseUrl: string = "http://127.0.0.1:8000/api/users";

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private router: Router) {
  }

  ngOnInit(): void {
    console.log("token retrieved is:: "+Global.TOKEN);
    this.http.get(this.baseUrl + "/all").subscribe(
      (data: any) => {
        console.log("list of users:: " + data[0].firstName + " " + data[0].lastName);
      },
      error => console.log("Error occurred in requesting...")
    );
  }
}
