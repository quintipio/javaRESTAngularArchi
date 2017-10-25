import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location }                 from '@angular/common';
import { Profil} from "../../../model/Profil";
import {ProfilService} from "../../../service/ProfilService";

@Component({
  selector: 'edit-profil',
  templateUrl: './editProfil.component.html'
})
export class EditProfilComponent implements OnInit {

  @Input() profil = new Profil;
  isModif: boolean;

  constructor(
    private profilService: ProfilService,
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {this.profil.id = +params['id'] || null});
    console.log(this.profil.id);
    this.isModif = this.profil.id != null;
    if(this.isModif) {
      this.profilService.getProfil(this.profil.id).subscribe( profil => this.profil = profil);
      console.log(this.profil);
    }
  }

  goBack(): void {
    this.location.back();
  }

  save() {
    if (this.isModif) {
      this.profilService.update(this.profil)
    } else {
      this.profilService.create(this.profil)
    }
    this.goBack();
  }

}
