import { Component, OnInit } from '@angular/core';
import {Stomp} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {Post} from "../model/post";
import {FormBuilder} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-messagetest',
  templateUrl: './messagetest.component.html',
  styleUrls: ['./messagetest.component.css']
})
export class MessagetestComponent implements OnInit {

  title = 'Notifications using Web Sockets';
  //description = 'Notifications';

  notifications:Notification[] = []
  baseUrl: string = "http://127.0.0.1:8000/api/v1/notifications";

  greetings: string[] = [];
  disabled = true;
  name: string | undefined;
  private stompClient = null;

  constructor(private fb: FormBuilder,
              private http: HttpClient,
              private router: Router) { }

  ngOnInit(): void {
  }
  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.greetings = [];
     // this.notifications = []
    }
  }

  connect() {
    const socket = new SockJS('http://127.0.0.1:8000/gkz-stomp-endpoint');
    // @ts-ignore
    this.stompClient = Stomp.over(socket);

    const _this = this;
    // @ts-ignore
    this.stompClient.connect({}, function (frame: string) {
      _this.setConnected(true);
      console.log('Connected: ' + frame);

      // @ts-ignore
      _this.stompClient.subscribe('/api/hi', function (response){
        console.log("Server response", response)
        _this.showGreeting(JSON.parse(response.body));
      });
    });
    this.http.get<Notification[]>(this.baseUrl).subscribe(
      data => {
        this.notifications = data
      },
      error => console.error("Notifications get error !")
    );
  }

  disconnect() {
    if (this.stompClient != null) {
      // @ts-ignore
      this.stompClient.disconnect();
    }

    this.setConnected(false);
    console.log('Disconnected!');
  }

  sendName() {
    // @ts-ignore
    this.stompClient.send(
      '/api/v1/notifications',
      {},
      JSON.stringify({ 'name': this.name })
    );
  }

  showGreeting(message: string) {
    this.greetings.push(message);
  }
}
