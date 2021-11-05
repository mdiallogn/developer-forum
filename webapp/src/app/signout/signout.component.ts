import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtClientService } from '../services/jwt-client.service';

@Component({
  selector: 'app-signout',
  templateUrl: './signout.component.html',
  styleUrls: ['./signout.component.css']
})
export class SignoutComponent implements OnInit {

  constructor(private jwtService: JwtClientService, private router: Router) { }

  ngOnInit(): void {
    this.jwtService.disconnect();
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigateByUrl('/home');
    // window.location.replace(window.location.host + "/home")
  }

}
