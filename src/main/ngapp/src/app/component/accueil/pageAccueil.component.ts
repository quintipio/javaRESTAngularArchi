import {Component, OnInit} from '@angular/core';

@Component({
  selector:'page-utilisateur',
  templateUrl:'./pageAccueil.component.html',
})
export class PageAccueilComponent implements OnInit{

  texte : string;

  ngOnInit(): void {
    this.texte = "page accueil"
  }
}
