import {Component, OnInit} from '@angular/core';
import {AdminService} from '../../service/AdminService';

@Component({
  selector:'page-admin',
  templateUrl:'./pageAdmin.component.html',
})
export class PageAdminComponent implements OnInit{
  texte;

  constructor(private adminService: AdminService) {
  }

  ngOnInit(): void {
    this.texte = this.adminService.getAccueil();
  }
}
