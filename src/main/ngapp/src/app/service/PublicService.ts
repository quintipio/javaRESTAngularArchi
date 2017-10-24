import { Injectable }    from '@angular/core';
import {  Http} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class PublicService {

  private publicurl = '/public';

  constructor(private http: Http) { }

  getAccueil() {
    return this.http.get(this.publicurl+"/accueil")
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
