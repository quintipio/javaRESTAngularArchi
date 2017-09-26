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

  constructor(private http: Http) { }

  getProfils(): Observable<Profil[]> {
    return this.http.get(this.profilurl).map((res:Response) => res.json());
  }

  getProfil(id :number) : Observable<Profil> {
    const url = '${this.profilurl}/${id}';
    return this.http.get(url).map((res:Response) => res.json());
  }

  delete(id :number) : Observable<Response> {
    const url = '${this.profilurl}/${id}';
    return this.http.delete(url);
  }

  create(profil: Profil) : Observable<Response> {
    return this.http.post(this.profilurl, JSON.stringify(profil), {headers: this.headers});
  }

  update(profil: Profil) : Observable<Response> {
    return this.http.put(this.profilurl, JSON.stringify(profil), {headers: this.headers});
  }
}
