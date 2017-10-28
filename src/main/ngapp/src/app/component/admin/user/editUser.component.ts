import {Component, Input, OnInit} from "@angular/core";
import {AdminService} from "../../../service/AdminService";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../../model/User";
import { Location } from '@angular/common';

@Component({
  selector:'edit-user',
  templateUrl:'./editUser.component.html'
})
export class editUserComponent implements OnInit {

  @Input() user = new User();
  isModif: boolean;

  constructor( private route: ActivatedRoute,
              private adminService:AdminService,
              private location: Location) {

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {this.user.id = +params['id'] || null});
    this.isModif = this.user.id != null;
    if(this.isModif) {
      this.adminService.getUser(this.user.id).subscribe( user => this.user = user);
    }
  }
  goBack(): void {
    this.location.back();
  }

  save() {
    if (this.isModif) {
      this.adminService.updateUser(this.user)
    } else {
      this.adminService.createUser(this.user)
    }
    this.goBack();
  }

  checkSso() {
    this.adminService.checkSso(this.user.sso);
  }

}
