import {Component, OnInit} from '@angular/core';
import {PublicService} from "../../service/PublicService";
import {ActivatedRoute} from '@angular/router';

@Component({
  selector:'page-reinit-mdp',
  templateUrl:'./pageReinitMdp.component.html',
})
export class PageReinitMdpComponent implements OnInit{

  link : string;
  mdpA : string;
  mdpB : string;
  errorMdp:boolean;

  constructor(private publicService: PublicService,private route:ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => this.link = params['key'] || "none");
  }

  checkPassword() {
    this.errorMdp = (this.mdpA !== this.mdpB);
  }

  reinit(){
    if(!this.errorMdp) {
      this.publicService.reinitMdp(this.link,this.mdpA);
    }
  }

}
