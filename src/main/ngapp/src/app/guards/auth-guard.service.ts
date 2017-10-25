import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {tokenNotExpired} from 'angular2-jwt';

import {TOKEN_NAME} from "../service/auth.constant"
import {UserService} from "../service/user.service"

@Injectable()
export class AuthGuard implements CanActivate {
  constructor(private router: Router, private userService: UserService) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (tokenNotExpired(TOKEN_NAME, this.userService.accessToken)) {
      return true;
    } else {
      this.router.navigate(['connexion'], {queryParams: {redirectTo: state.url}});
      return false;
    }
  }
}
