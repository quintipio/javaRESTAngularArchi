import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AdminService} from "../../../service/AdminService";
import {User} from "../../../model/User";
import {Router} from "@angular/router";

@Component({
    selector: 'app-gerer-user',
    templateUrl: './gererUser.component.html',encapsulation: ViewEncapsulation.None,

})
export class GererUserComponent implements OnInit {

  users : User[];

  constructor(private router:Router, private adminService : AdminService) {
  }

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers() {
    this.adminService.getAllUsers().subscribe(res => this.users = res);
  }

  add() {
    this.router.navigate(['user/edit']);
  }

  modify(user : User) {
    this.router.navigate(['user/edit'], {queryParams : {id : user.id}});
  }

  delete(user : User) {
    this.adminService.deleteUser(user.id).then(()=> this.getUsers());
  }
}
