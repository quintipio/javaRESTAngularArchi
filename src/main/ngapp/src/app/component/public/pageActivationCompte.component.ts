import {Component, OnInit} from '@angular/core';
import {PublicService} from "../../service/PublicService";
import {ActivatedRoute} from '@angular/router';

@Component({
  selector:'page-activation-compte',
  templateUrl:'./pageActivationCompte.component.html',
})
export class PageActivationCompteComponent implements OnInit{

  errorMsg: boolean;
  ok:boolean;

  error: string;
  key:string;

  constructor(
    private publicService: PublicService, private route: ActivatedRoute) {
    this.errorMsg = false;
    this.ok = false;
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => this.key = params['key'] || "none");
    if(this.key != "none") {
      this.publicService.activerCompte(this.key);
      this.errorMsg = false;
      this.ok = true;
    }
    else {
      this.errorMsg = true;
      this.ok = false;
      this.error = "Erreur sur votre lien d'activation";
    }
  }


}
