import {Component, OnInit} from '@angular/core';

@Component({
  selector:'page-utilisateur',
  templateUrl:'./pageUtilisateur.component.html',
})
export class PageUtilisateurComponent implements OnInit{
  texte : string;


  ngOnInit(): void {
    this.texte = "page utilisateur 1"
  }
}
