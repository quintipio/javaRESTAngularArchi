import {Component, OnInit} from '@angular/core';
import {PublicService} from "../../service/PublicService";
import {Router} from '@angular/router';

@Component({
  selector:'page-demande-reinit-mdp',
  templateUrl:'./pageDemandeReinitMdp.component.html',
})
export class PageDemandeReinitMdpComponent implements OnInit{

  sso :string;
  message: boolean;

  constructor(
    private publicService: PublicService, private router: Router) {}

  ngOnInit(): void {
    this.message = false;
  }

  reinit(){
    this.publicService.demandeReinitMdp(this.sso);
    this.message = true;
  }

  retour() {
    this.router.navigate(['/']);
  }
}
