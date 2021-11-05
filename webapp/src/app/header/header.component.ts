import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtClientService } from '../services/jwt-client.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isAuth:boolean = false;
  constructor(private jwtService: JwtClientService, private router: Router) { }

  ngOnInit(): void {
    this.jwtService.refresh();
    this.isAuth = JwtClientService.isAuth;
  }

  logout() {
    this.jwtService.disconnect()
    window.location.reload()
  }
}
