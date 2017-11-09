import { Injectable }    from '@angular/core';
import {  Http} from '@angular/http';
import 'rxjs/add/operator/map';
import {TranslateService} from '../translate/translate.service';
import {environment} from "../../environments/environment";

@Injectable()
export class PublicService {

  private publicurl = environment.apiAdress + '/public';

  constructor(private http: Http,
              private _translate : TranslateService) { }

  getAccueil() {
    return this.http.get(this.publicurl+"/accueil"+this._translate.urlServerlangue(false))
      .map(response => response.text());
  }

  demandeReinitMdp(sso : string) {
    const url = this.publicurl+`/demandeReinitMdp?sso=${sso}`;
    this.http.get(url).subscribe();
  }

  reinitMdp(link:string, mdp: string) {
    const url = this.publicurl+`/reinitMdp?link=${link}&newmdp=${mdp}`;
    this.http.get(url).subscribe();
  }

  activerCompte(key:string) {
    const url = this.publicurl+`/activerCompte?link=${key}`;
    this.http.get(url).subscribe();
  }
}
