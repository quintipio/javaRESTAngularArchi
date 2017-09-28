import { Injectable }    from '@angular/core';
import { Headers, Http, Response } from '@angular/http';
import {Profil} from "../model/Profil";
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';

@Injectable()
export class ProfilService {
  private profilurl = 'http://localhost:8080/profil';
  private headers = new Headers({'Content-Type': 'application/json'});

/*,'Access-Control-Allow-Origin':'http://localhost:8080','Access-Control-Allow-Methods':'GET PUT POST DELETE'*/

  constructor(private http: Http) { }

  getProfils(): Promise<Profil[]> {
    return this.http.get(this.profilurl)
      .toPromise()
      .then(response => response.json() as Profil[])
      .catch(this.handleError);
  }

  getProfil(id :number) : Observable<Profil> {
    const url = `${this.profilurl}/${id}`;
    return this.http.get(url).map((res:Response) => res.json());
  }

  getprofilByLibelle(libelle: string): Promise<Profil[]> {
    const url = this.profilurl+`/search?libelle=${libelle}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as Profil[])
      .catch(this.handleError);
  }

  create(profil: Profil): Promise<Profil> {
    return this.http
      .post(this.profilurl, JSON.stringify(profil), {headers : this.headers})
      .toPromise()
      .then(res => res.json() as Profil)
      .catch(this.handleError);
  }

  update(profil: Profil): Promise<Profil> {
    return this.http
      .put(this.profilurl, JSON.stringify(profil), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as Profil)
      .catch(this.handleError);
  }

  delete(id: number): Promise<void> {
    const url = `${this.profilurl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    return Promise.reject(error.message || error);
  }
}
