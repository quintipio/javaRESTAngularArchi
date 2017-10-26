import { Injectable }    from '@angular/core';
import {AuthHttp} from 'angular2-jwt';
import 'rxjs/add/operator/map';
import {TranslateService} from '../translate/translate.service';

@Injectable()
export class AdminService {
  private publicurl = '/admin';

  constructor(private http: AuthHttp, private _translate : TranslateService) { }

  getAccueil() {
    return this.http.get(this.publicurl+"/accueil"+this._translate.urlServerlangue(false))
      .map(response => response.text());
  }
}
