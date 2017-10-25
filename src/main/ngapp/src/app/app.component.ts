import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from './service/user.service';
import {TOKEN_LOGIN} from './service/auth.constant';
import {CommunicationErrorService} from './service/CommunicationErrorService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit{


  constructor(private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    if(this.userService.autoLogin()) {
      this.router.navigate(['/']);
    }
  }

  logout() {
    this.userService.logout();
    this.router.navigate(['/']);
  }

  goToConnect() {
    this.router.navigate(['/connexion']);
  }

  get isConnected() {
    return this.userService.isConnected();
  }

  get isAdminUser() {
    return this.userService.isAdminUser();
  }

  get userLogin() {
    if(sessionStorage.getItem(TOKEN_LOGIN) != null) {
      return sessionStorage.getItem(TOKEN_LOGIN);
    }
    else {
      return "";
    }
  }
}
