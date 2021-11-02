import { Component, OnInit } from '@angular/core';
import {Stomp} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';

@Component({
  selector: 'app-messagetest',
  templateUrl: './messagetest.component.html',
  styleUrls: ['./messagetest.component.css']
})
export class MessagetestComponent implements OnInit {

  title = 'Web Socket Test';
  description = 'Middleware Project';

  greetings: string[] = [];
  disabled = true;
  name: string | undefined;
  private stompClient = null;

  constructor() { }

  ngOnInit(): void {
  }
  setConnected(connected: boolean) {
    this.disabled = !connected;

    if (connected) {
      this.greetings = [];
    }
  }

  connect() {
    const socket = new SockJS('http://localhost:8000/gkz-stomp-endpoint');
    // @ts-ignore
    this.stompClient = Stomp.over(socket);

    const _this = this;
    // @ts-ignore
    this.stompClient.connect({}, function (frame: string) {
      _this.setConnected(true);
      console.log('Connected: ' + frame);

      // @ts-ignore
      _this.stompClient.subscribe('/api/hi', function (response){
        _this.showGreeting(JSON.parse(response.body));
      });
    });
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
      '/gkz/test',
      {},
      JSON.stringify({ 'name': this.name })
    );
  }

  showGreeting(message: string) {
    this.greetings.push(message);
  }
}
