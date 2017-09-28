import { Component, OnInit } from '@angular/core';
import { Router }            from '@angular/router';
import { Subject }           from 'rxjs/Subject';

import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/switchMap';

import {ProfilService} from "../../service/ProfilService";
import {Profil} from "../../model/Profil";

@Component({
  selector:'profil-search',
  templateUrl: './searchProfil.component.html',
  styleUrls: ['./searchProfil.component.css'],
  providers: [ProfilService]
  }
)
export class SearchprofilComponent {

  profils : Profil[];

  constructor(
    private profilService: ProfilService,
    private router : Router) {}

  search(term: string): void {
    this.profilService.getprofilByLibelle(term).then(res => this.profils = res as Profil[]);
  }

  gotoDetail(profil: Profil): void {
    this.router.navigate(['profils/edit'], {queryParams : {id : profil.id}});
  }
}
