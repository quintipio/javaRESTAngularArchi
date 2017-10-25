import {Injectable} from '@angular/core';
import { Headers, Response } from '@angular/http';
import {AuthHttp} from 'angular2-jwt';
import 'rxjs/add/operator/map';
import {User} from '../model/User';
import {Observable} from 'rxjs/Observable';
import {toPromise} from 'rxjs/operator/toPromise';

@Injectable()
export class StandardService {
  private publicurl = '/standard';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: AuthHttp) { }

  getAccueil() {
    return this.http.get(this.publicurl+"/accueil")
      .map(response => response.text());
  }

  getData() : Observable<User> {
    return this.http.get(this.publicurl+"/updateUser")
      .map((res:Response) => res.json() as User);
  }

  updateData(user : User) : Promise<User> {
    return this.http.put(this.publicurl+"/updateUser", JSON.stringify(user), {headers : this.headers}).toPromise()
      .then(res => res.json() as User);
  }

  updatePassword(oldPass:string, newPass:string): Promise<boolean> {
    const url = this.publicurl+`/updatePassword?oldPass=${oldPass}&newPass=${newPass}`;
    return this.http.get(url)
      .toPromise()
      .then(
      res => {
      return (res.text() === "ok");
    });
  }

  deleteCompte() {
    this.http.delete(this.publicurl+"/supprimerCompte").toPromise()
      .then(res => res.text());
  }

}
