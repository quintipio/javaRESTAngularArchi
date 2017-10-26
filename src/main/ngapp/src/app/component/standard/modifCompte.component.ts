import {Component, Input, OnInit} from '@angular/core';
import {StandardService} from '../../service/StandardService';
import {User} from '../../model/User';
import {UserService} from '../../service/user.service';
import {Router} from '@angular/router';
import {TranslateService} from '../../translate/translate.service';

@Component({
  selector:'modif-compte',
  templateUrl:'./modifCompte.component.html',
})
export class ModifCompteComponent implements OnInit{

  @Input() utilisateur = new User;

  error:string;

  oldPass:string;
  newPass:string;
  newPassconfrm:string;
  errorMdp:boolean;

  constructor(private standardService : StandardService,
              private userService : UserService,
              private router : Router,
              private _translate : TranslateService) {}

  ngOnInit(): void {
    this.standardService.getData().subscribe(res => this.utilisateur = res);
  }

  saveInfoProfil() : void {
    this.standardService.updateData(this.utilisateur).then(res => this.utilisateur = res);
  }

  savePassword(): void {
    this.checkPassword();
    if(!this.errorMdp) {
      this.standardService.updatePassword(this.oldPass,this.newPass).then(res => {
        if(!res) {
          this.error = this._translate.instant('Ancien mdp incorrect');
        }
      });
    }
  }

  checkPassword() {
    this.errorMdp = (this.newPass !== this.newPassconfrm);
  }

  supprimerCompte(): void {
    this.standardService.deleteCompte();
    this.userService.logout();
    this.router.navigate(['/']);
  }
}
