import { Component, OnInit } from '@angular/core';
import {Profil} from '../../../model/Profil';
import {ProfilService} from "../../../service/ProfilService"
import {Router} from "@angular/router";

@Component({
  selector:'liste-Profil',
  templateUrl:'./listeProfil.component.html',
})
export class ListeProfilComponent implements OnInit {

  profils: Profil[];

  constructor(
    private router:Router,
    private profilService: ProfilService) {}

  ngOnInit(): void {
    this.getProfils();
  }

  getProfils() {
    this.profilService.getProfils().then(profilsRes => this.profils = profilsRes);
  }


  delete(profil:Profil) {
    this.profilService.delete(profil.id).then(() => this.getProfils());
  }

  add() {
    this.router.navigate(['profils/edit']);
  }

  edit(profil: Profil) {
    this.router.navigate(['profils/edit'], {queryParams : {id : profil.id}});
  }

}
