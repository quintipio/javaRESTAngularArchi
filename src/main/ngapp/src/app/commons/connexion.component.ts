import {Component, OnInit} from '@angular/core';
import {UserService} from '../service/user.service';

@Component({
  selector:'connexion',
  templateUrl:'./connexion.component.html',
})
export class ConnexionComponent implements OnInit{


  constructor(private userService : UserService) {}

  ngOnInit(): void {
  }

  logout() {
    this.userService.logout();
  }

  get isConnected() {
    return this.userService.isConnected();
  }
}
