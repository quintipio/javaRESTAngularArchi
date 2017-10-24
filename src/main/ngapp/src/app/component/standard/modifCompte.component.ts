import {Component, Input, OnInit} from '@angular/core';
import {StandardService} from '../../service/StandardService';
import {User} from '../../model/User';
import {UserService} from '../../service/user.service';
import {Router} from '@angular/router';

@Component({
  selector:'modif-compte',
  templateUrl:'./modifCompte.component.html',
})
export class ModifCompteComponent implements OnInit{

  @Input() utilisateur = new User;

  oldPass:string;
  newPass:string;
  newPassconfrm:string;

  constructor(private standardService : StandardService, private userService : UserService, private router : Router) {}

  ngOnInit(): void {
    this.standardService.getData().subscribe(res => this.utilisateur = res);
  }

  saveInfoProfil() : void {
    this.standardService.updateData(this.utilisateur).then(res => this.utilisateur = res);
  }

  savePassword(): void {
    this.standardService.updatePassword(this.oldPass,this.newPass);
  }

  supprimerCompte(): void {
    this.standardService.deleteCompte();
    this.userService.logout();
    this.router.navigate(['/']);
  }
}
