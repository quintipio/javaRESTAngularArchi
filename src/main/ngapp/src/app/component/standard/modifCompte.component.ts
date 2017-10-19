import {Component, Input, OnInit} from '@angular/core';
import {StandardService} from '../../service/StandardService';
import {User} from '../../model/User';

@Component({
  selector:'modif-compte',
  templateUrl:'./modifCompte.component.html',
})
export class ModifCompteComponent implements OnInit{

  @Input() utilisateur = new User;

  oldPass:string;
  newPass:string;
  newPassconfrm:string;

  constructor(private standardService : StandardService) {}

  ngOnInit(): void {
    this.standardService.getData().subscribe(res => this.utilisateur = res);
  }

  saveInfoProfil() : void {
    this.standardService.updateData(this.utilisateur).then(res => this.utilisateur = res);
  }

  savePassword(): void {
    this.standardService.updatePassword(this.oldPass,this.newPass);
  }
}
