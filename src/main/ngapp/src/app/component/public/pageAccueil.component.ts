import {Component, OnInit} from '@angular/core';
import {PublicService} from "../../service/PublicService";

@Component({
  selector:'page-utilisateur',
  templateUrl:'./pageAccueil.component.html',
})
export class PageAccueilComponent implements OnInit{

  texte;

  constructor(
    private publicService: PublicService) {}

  ngOnInit(): void {
   this.texte = this.publicService.getAccueil();
  }
}
