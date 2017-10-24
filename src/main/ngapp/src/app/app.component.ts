import { Component } from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from './service/user.service';
import {TOKEN_LOGIN} from './service/auth.constant';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Archi FrontEnd  - Page principale';

  constructor(private router: Router, private userService: UserService) {
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/']);
  }

  goToConnect() {
    this.router.navigate(['/erreurConnexion']);
  }

  get isConnected() {
    return this.userService.isConnected();
  }

  get isAdminUser() {
    return this.userService.isAdminUser();
  }

  get userLogin() {
    if(localStorage.getItem(TOKEN_LOGIN) != null) {
      return localStorage.getItem(TOKEN_LOGIN);
    }
    else {
      return "";
    }
  }
}
