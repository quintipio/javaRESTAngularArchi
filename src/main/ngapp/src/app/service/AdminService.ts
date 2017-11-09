import { Injectable }    from '@angular/core';
import { Response } from '@angular/http';
import {AuthHttp} from 'angular2-jwt';
import 'rxjs/add/operator/map';
import {TranslateService} from '../translate/translate.service';
import {Observable} from "rxjs/Observable";
import {User} from "../model/User";
import {environment} from "../../environments/environment";

@Injectable()
export class AdminService {
  private adminurl = environment.apiAdress + '/admin';
  private headers = new Headers({'Content-Type': 'application/json'});

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

  checkSso(sso:string,id:number) : Observable<boolean> {
    const url = this.adminurl+`/utilisateur/checkSso?id=${id}&sso=${sso}`;
    return this.http.get(url).map(res => res.text().toLocaleUpperCase()==="TRUE");
  }

  getUser(id:number) :Observable<User>{
    const url = `${this.adminurl}/utilisateur/${id}`;
    return this.http.get(url).map((res:Response) => res.json() as User);
  }

  updateUser(user:User): Promise<User> {
    return this.http
      .put(this.adminurl+`/utilisateur`, JSON.stringify(user))
      .toPromise()
      .then(res => res.json() as User);
  }

  createUser(user: User): Promise<User> {
    return this.http
      .post(this.adminurl+`/utilisateur`, JSON.stringify(user))
      .toPromise()
      .then(res => res.json() as User);
  }

  activerUser(user : User) {
    const url = this.adminurl+`/utilisateur/activer/${user.id}`;
    this.http.get(url).subscribe();
  }

  desactiverUser(user: User){
    const url = this.adminurl+`/utilisateur/desactiver/${user.id}`;
    this.http.get(url).subscribe();
  }

  changeMdp(mdp: string, id:number) {
    const url = this.adminurl+`/utilisateur/updatePassword?id=${id}&mdp=${mdp}`;
    this.http.get(url).subscribe();
  }
}
