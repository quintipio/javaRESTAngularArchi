import {Injectable} from '@angular/core';
import {JwtHelper} from 'angular2-jwt';
import {TOKEN_NAME, TOKEN_AUTHORITIES, TOKEN_LOGIN,ROLE_ADMIN} from "./auth.constant";
import {TranslateService} from '../translate/translate.service';
import {StandardService} from './StandardService';

@Injectable()
export class UserService {
  jwtHelper: JwtHelper = new JwtHelper();
  accessToken: string;
  isConnectedUser: boolean;

  constructor(private standardService: StandardService, private _translate : TranslateService) {
  }

  login(accessToken: string,remember:boolean) {
    const decodedToken = this.jwtHelper.decodeToken(accessToken);
    this.accessToken = accessToken;
    this.isConnectedUser = true;
    sessionStorage.setItem(TOKEN_NAME, accessToken);
    sessionStorage.setItem(TOKEN_LOGIN,decodedToken.user_name);
    sessionStorage.setItem(TOKEN_AUTHORITIES,JSON.stringify(decodedToken.authorities));
    if(remember) {
      localStorage.setItem(TOKEN_NAME, accessToken);
      localStorage.setItem(TOKEN_LOGIN,decodedToken.user_name);
      localStorage.setItem(TOKEN_AUTHORITIES,JSON.stringify(decodedToken.authorities));
    }

    this.standardService.getLangue().subscribe(res => this._translate.use(res));
  }

  autoLogin() {
    if(!this.isConnectedUser) {
      if(localStorage.getItem(TOKEN_AUTHORITIES) != null) {
        sessionStorage.setItem(TOKEN_NAME, localStorage.getItem(TOKEN_NAME));
        sessionStorage.setItem(TOKEN_LOGIN,localStorage.getItem(TOKEN_LOGIN));
        sessionStorage.setItem(TOKEN_AUTHORITIES,localStorage.getItem(TOKEN_AUTHORITIES));
        this.standardService.getLangue().subscribe(res => this._translate.use(res));
        return true;
      }
    }
    return false;
  }

  logout() {
    this.accessToken = null;
    this.isConnectedUser = false;
    sessionStorage.removeItem(TOKEN_NAME);
    sessionStorage.removeItem(TOKEN_AUTHORITIES);
    sessionStorage.removeItem(TOKEN_LOGIN);
    if(localStorage.getItem(TOKEN_AUTHORITIES) != null) {
      localStorage.removeItem(TOKEN_NAME);
      localStorage.removeItem(TOKEN_AUTHORITIES);
      localStorage.removeItem(TOKEN_LOGIN);
    }
  }

  isAdminUser(): boolean {
    if(sessionStorage.getItem(TOKEN_AUTHORITIES) != null) {
      var storedAuto = JSON.parse(sessionStorage.getItem(TOKEN_AUTHORITIES));
      return storedAuto.some(el => el === ROLE_ADMIN);
    }
    return false;

  }

  isConnected(): boolean {
    return sessionStorage.getItem(TOKEN_NAME) != null;
  }
}
