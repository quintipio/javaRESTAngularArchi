import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';

import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/switchMap';

import {User} from "../../../model/User";
import {AdminService} from "../../../service/AdminService";

@Component({
  selector:'user-search',
  templateUrl: './searchUser.component.html',
  styleUrls: ['./searchUser.component.css'],
  providers: [AdminService]
  }
)
export class SearchUserComponent {

  users : User[];

  constructor(
    private adminService: AdminService,
    private router : Router) {}

  search(term: string): void {
    this.adminService.getUsersByLibelle(term).then(res => this.users = res as User[]);
  }

  gotoDetail(user: User): void {
    this.router.navigate(['users/edit'], {queryParams : {id : user.id}});
  }
}
