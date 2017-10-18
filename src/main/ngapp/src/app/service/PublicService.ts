import { Injectable }    from '@angular/core';
import { Headers, Http} from '@angular/http';
import 'rxjs/add/operator/map';

@Injectable()
export class PublicService {

  private publicurl = '/public';

  constructor(private http: Http) { }

  getAccueil() {
    return this.http.get(this.publicurl+"/accueil")
      .map(response => response.text());
  }
}
