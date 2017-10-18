import { Injectable }    from '@angular/core';
import {AuthHttp} from 'angular2-jwt';
import 'rxjs/add/operator/map';

@Injectable()
export class StandardService {
  private publicurl = '/standard';

  constructor(private http: AuthHttp) { }

  getAccueil() {
    return this.http.get(this.publicurl+"/accueil")
      .map(response => response.text());
  }
}
