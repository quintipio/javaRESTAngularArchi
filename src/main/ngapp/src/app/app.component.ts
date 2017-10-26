import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from './service/user.service';
import {LANGUE_DEFAUT, LANGUES_DISPO, TOKEN_LOGIN} from './constantes';
import {TranslateService} from './translate/translate.service';
import {StandardService} from './service/StandardService';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit{


  public supportedLanguages : any[];

  constructor(private router: Router,
              private userService: UserService,
              private _translate: TranslateService,
              private standardService : StandardService) {
  }

  ngOnInit(): void {

    this.supportedLanguages = LANGUES_DISPO;
    this.selectLang(LANGUE_DEFAUT);

    if(this.userService.autoLogin()) {
      this.router.navigate(['/']);
    }
  }

  isCurrentLang(lang: string) {
    return lang === this._translate.currentLang;
  }

  selectLang(lang:string) {
    this._translate.use(lang);
    if(this.isConnected) {
      this.standardService.updateLangue(lang);
    }
  }



  logout() {
    this.userService.logout();
    this.router.navigate(['/']);
  }

  goToConnect() {
    this.router.navigate(['/connexion']);
  }

  get isConnected() {
    return this.userService.isConnected();
  }

  get isAdminUser() {
    return this.userService.isAdminUser();
  }

  get userLogin() {
    if(sessionStorage.getItem(TOKEN_LOGIN) != null) {
      return sessionStorage.getItem(TOKEN_LOGIN);
    }
    else {
      return "";
    }
  }
}
