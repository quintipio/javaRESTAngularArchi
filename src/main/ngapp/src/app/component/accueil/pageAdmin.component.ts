import {Component, OnInit} from '@angular/core';

@Component({
  selector:'page-admin',
  templateUrl:'./pageAdmin.component.html',
})
export class PageAdminComponent implements OnInit{
  texte : string;


  ngOnInit(): void {
    this.texte = "page Admin"
  }
}
