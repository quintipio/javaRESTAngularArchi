import { Injectable }    from '@angular/core';
import { Headers, Response } from '@angular/http';
import {Profil} from "../model/Profil";
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import {AuthHttp} from 'angular2-jwt';

@Injectable()
export class ProfilService {
  private profilurl = 'admin/profil';
  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: AuthHttp) { }

  getProfils(): Promise<Profil[]> {
    return this.http.get(this.profilurl)
      .toPromise()
      .then(response => response.json() as Profil[]);
  }

  getProfil(id :number) : Observable<Profil> {
    const url = `${this.profilurl}/${id}`;
    return this.http.get(url).map((res:Response) => res.json());
  }

  getprofilByLibelle(libelle: string): Promise<Profil[]> {
    const url = this.profilurl+`/search?libelle=${libelle}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as Profil[]);
  }

  create(profil: Profil): Promise<Profil> {
    return this.http
      .post(this.profilurl, JSON.stringify(profil), {headers : this.headers})
      .toPromise()
      .then(res => res.json() as Profil);
  }

  update(profil: Profil): Promise<Profil> {
    return this.http
      .put(this.profilurl, JSON.stringify(profil), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as Profil);
  }

  delete(id: number): Promise<void> {
    const url = `${this.profilurl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null);
  }
}
