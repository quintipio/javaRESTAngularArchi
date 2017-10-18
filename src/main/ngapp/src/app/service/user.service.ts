import {Injectable} from '@angular/core';
import {JwtHelper} from 'angular2-jwt';
import {TOKEN_NAME, TOKEN_AUTHORITIES, TOKEN_LOGIN,ROLE_ADMIN} from "./auth.constant";

@Injectable()
export class UserService {
  jwtHelper: JwtHelper = new JwtHelper();
  accessToken: string;
  isConnectedUser: boolean;

  constructor() {
  }

  login(accessToken: string) {
    const decodedToken = this.jwtHelper.decodeToken(accessToken);
    this.accessToken = accessToken;
    this.isConnectedUser = true;
    localStorage.setItem(TOKEN_NAME, accessToken);
    localStorage.setItem(TOKEN_LOGIN,decodedToken.user_name);
    localStorage.setItem(TOKEN_AUTHORITIES,JSON.stringify(decodedToken.authorities));
  }

  logout() {
    this.accessToken = null;
    this.isConnectedUser = false;
    localStorage.removeItem(TOKEN_NAME);
    localStorage.removeItem(TOKEN_AUTHORITIES);
    localStorage.removeItem(TOKEN_LOGIN);
  }

  isAdminUser(): boolean {
    if(localStorage.getItem(TOKEN_AUTHORITIES) != null) {
      var storedAuto = JSON.parse(localStorage.getItem(TOKEN_AUTHORITIES));
      return storedAuto.some(el => el === ROLE_ADMIN);
    }
    return false;

  }

  isConnected(): boolean {
    return localStorage.getItem(TOKEN_NAME) != null;
  }
}
