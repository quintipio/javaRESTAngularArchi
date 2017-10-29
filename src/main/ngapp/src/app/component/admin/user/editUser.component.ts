import {Component, Input, OnInit} from "@angular/core";
import {AdminService} from "../../../service/AdminService";
import {ActivatedRoute} from "@angular/router";
import {User} from "../../../model/User";
import { Location } from '@angular/common';
import {Profil} from "../../../model/Profil";
import {ProfilService} from "../../../service/ProfilService";
import {LANGUES_DISPO} from "../../../constantes";

@Component({
  selector:'edit-user',
  templateUrl:'./editUser.component.html'
})
export class EditUserComponent implements OnInit {

  @Input() user = new User();

  isModif: boolean;
  isSsoExist: boolean;

  dropdownListProfils = [];
  selectedItemsProfils = [];
  dropdownSettings = {};

  langues= [];


  newMdp: string;
  newMdpConfirm:string;
  errorMdp: boolean;

  constructor(private route: ActivatedRoute,
              private adminService:AdminService,
              private profilService:ProfilService,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {this.user.id = +params['id'] || null});
    this.isModif = this.user.id != null;

    this.profilService.getProfils().then(res => this.createDropdownProfils(res));

    this.dropdownSettings = {
      singleSelection: false,
      text:"Select",
      selectAllText:'Select All',
      unSelectAllText:'UnSelect All',
      enableSearchFilter: true
    };

    this.langues = LANGUES_DISPO;

    if(this.isModif) {
      this.adminService.getUser(this.user.id).subscribe( user => this.loadUser(user));

    }
  }

  loadUser(user:User) {
    this.user = user;
    this.selectedItemsProfils = this.user.userProfiles;
    var i;
    for(i = 0; i < this.selectedItemsProfils.length; i++){
      this.selectedItemsProfils[i].itemName = this.selectedItemsProfils[i]['libelle'];
      delete this.selectedItemsProfils[i].libelle;
    }
  }

  createDropdownProfils(profils: Profil[]) {
    this.dropdownListProfils = profils;
    var i;
    for(i = 0; i < this.dropdownListProfils.length; i++){
      this.dropdownListProfils[i].itemName = this.dropdownListProfils[i]['libelle'];
      delete this.dropdownListProfils[i].libelle;
    }
  }

  onItemSelect(item:any){
    this.changeUserProfiles();
  }
  OnItemDeSelect(item:any){
    this.changeUserProfiles();
  }

  changeUserProfiles(){
    var listTmp = this.selectedItemsProfils;
    var i;
    for(i = 0; i < listTmp.length; i++){
      listTmp[i].libelle = listTmp[i]['itemName'];
      delete listTmp[i].itemName;
    }
    this.user.userProfiles = listTmp;
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
    this.adminService.checkSso(this.user.sso,this.user.id).subscribe(res => this.isSsoExist = res);
  }


  /**************************
   * PARTIE MOT DE PASSE
   **************************/


  savePassword(): void {
    this.checkPassword();
    if(!this.errorMdp) {
      this.adminService.changeMdp(this.newMdp,this.user.id);
    }
  }

  checkPassword() {
    this.errorMdp = (this.newMdp !== this.newMdpConfirm);
  }

}
