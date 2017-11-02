import {Component, Input, OnInit} from "@angular/core";
import {AdminService} from "../../../service/AdminService";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../../model/User";
import { Location } from '@angular/common';
import {Profil} from "../../../model/Profil";
import {ProfilService} from "../../../service/ProfilService";
import {LANGUES_DISPO, PROFILS_DISPO} from "../../../constantes";

@Component({
  selector:'edit-user',
  templateUrl:'./editUser.component.html'
})
export class EditUserComponent implements OnInit {

  @Input() user = new User();

  isModif: boolean;
  isSsoExist: boolean;

  dropdownListProfils : Profil[] = PROFILS_DISPO;

  langues= [];

  newMdp: string;
  newMdpConfirm:string;
  errorMdp: boolean;
  errorMdpCreate: boolean;

  isMdpChange: boolean;

  constructor(private route: ActivatedRoute,
              private adminService:AdminService,
              private profilService:ProfilService,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {this.user.id = +params['id'] || null});
    this.isModif = this.user.id != null;

    this.profilService.getProfils().then(res => this.dropdownListProfils = res);

    this.langues = LANGUES_DISPO;

    if(this.isModif) {
      this.adminService.getUser(this.user.id).subscribe( user => this.user = user);
    }
    else {
      this.user.actif = false;
      this.user.langue = LANGUES_DISPO[0].value;
    }
  }

  goBack(): void {
    this.location.back();
  }

  save() {
    if(this.validate()) {
      if (this.isModif) {
        this.adminService.updateUser(this.user);
      } else {
          this.adminService.createUser(this.user);
      }
      this.goBack();
    }
  }

  checkSso() {
    this.adminService.checkSso(this.user.sso,this.user.id).subscribe(res => this.isSsoExist = res);
  }

  validate() : boolean {
    if(!this.isModif) {
      this.checkPasswordCreate();
      return !this.errorMdpCreate && !this.isSsoExist && this.user.userProfiles != undefined;
    } else {
      return !this.isSsoExist && this.user.userProfiles != undefined;
    }
  }


  /**************************
   * PARTIE MOT DE PASSE
   **************************/


  savePassword(): void {
    this.checkPassword();
    if(!this.errorMdp) {
      this.adminService.changeMdp(this.newMdp,this.user.id);
      this.isMdpChange = true;
    }
  }

  checkPassword() {
    this.errorMdp = (this.newMdp !== this.newMdpConfirm);
  }

  checkPasswordCreate() {
    this.errorMdpCreate = (this.user.motDePasse !== this.newMdpConfirm);
  }

}
