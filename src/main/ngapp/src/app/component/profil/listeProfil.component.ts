import { Component, OnInit } from '@angular/core';
import {Profil} from '../../model/Profil';
import {ProfilService} from "../../service/ProfilService"
import {Router} from "@angular/router";

@Component({
  selector:'liste-Profil',
  templateUrl:'./listeProfil.component.html',
})
export class ListeProfilComponent implements OnInit {

  profils: Profil[];
  selectedProfil: Profil;

  constructor(
    private router:Router,
    private profilService: ProfilService) {}

  ngOnInit(): void {
    this.getProfils();
  }

  getProfils() {
    this.profilService.getProfils().subscribe(data => this.profils = data);
  }

}
