import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Web Socket Test';
  description = 'Middleware Project';
  currentRoute: string = '';
  constructor(private router: Router){
    console.log(router.url);
    router.events.subscribe(event => 
           {
              this.currentRoute = this.router.url.replace('/', '');
           });
    }
}
