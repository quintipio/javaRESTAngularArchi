import { Injectable }    from '@angular/core';
import {AuthHttp} from 'angular2-jwt';
import 'rxjs/add/operator/map';
import {TranslateService} from '../translate/translate.service';
import {Observable} from "rxjs/Observable";
import {User} from "../model/User";

@Injectable()
export class AdminService {
  private adminurl = '/admin';

  constructor(private http: AuthHttp, private _translate : TranslateService) { }

  getAccueil() {
    return this.http.get(this.adminurl+"/accueil"+this._translate.urlServerlangue(false))
      .map(response => response.text());
  }

  getAllUsers() : Observable<User[]> {
    return this.http.get(this.adminurl+'/utilisateur').map( res => res.json() as User[]);
  }

  getUsersByLibelle(libelle: string): Promise<User[]> {
    const url = this.adminurl+`/utilisateur/search?sso=${libelle}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as User[]);
  }

  deleteUser(id) : Promise<void> {
    const url = this.adminurl+`/utilisateur/${id}`;
    return this.http.delete(url)
      .toPromise()
      .then(() => null);
  }
}
