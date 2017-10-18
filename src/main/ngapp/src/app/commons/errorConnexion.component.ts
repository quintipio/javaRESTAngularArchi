import {Component, OnInit} from '@angular/core';
import {UserService} from '../service/user.service';

@Component({
  selector:'erreur-connexion',
  templateUrl:'./errorConnexion.component.html',
})
export class ErrorConnexionComponent implements OnInit{


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
