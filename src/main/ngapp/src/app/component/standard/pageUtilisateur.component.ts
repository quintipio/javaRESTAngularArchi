import {Component, OnInit} from '@angular/core';
import {StandardService} from '../../service/StandardService';

@Component({
  selector:'page-utilisateur',
  templateUrl:'./pageUtilisateur.component.html',
})
export class PageUtilisateurComponent implements OnInit{
  texte;

  constructor(private standardService : StandardService) {}

  ngOnInit(): void {
    this.texte = this.standardService.getAccueil();
  }
}
