import {Component, OnInit} from '@angular/core';
import {PublicService} from "../../service/PublicService";
import {ActivatedRoute} from '@angular/router';
import {PasswordUtils} from '../../utils/passwordUtils';

@Component({
  selector:'page-reinit-mdp',
  templateUrl:'./pageReinitMdp.component.html',
})
export class PageReinitMdpComponent implements OnInit{

  link : string;
  mdpA : string;
  mdpB : string;

  errorMdp: boolean;
  error:string;

  constructor(private publicService: PublicService,private route:ActivatedRoute) {}

  ngOnInit(): void {
    this.errorMdp = false;
    this.route.queryParams.subscribe(params => this.link = params['key'] || "none");
  }

  reinit(){
    this.error = PasswordUtils.checkpassword(this.mdpA,this.mdpB);
    if(this.error != null && this.error.length > 0) {
      this.errorMdp = true;
    } else {
      this.publicService.reinitMdp(this.link,this.mdpA);
    }
  }

}
