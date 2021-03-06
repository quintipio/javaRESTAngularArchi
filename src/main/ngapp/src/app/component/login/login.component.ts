import {Component, OnInit} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';

import {AuthenticationService} from "../../service/authentication.service"
import {UserService} from "../../service/user.service";
import {TranslateService} from '../../translate/translate.service';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  model: any = {};
  loading = false;
  error = '';
  redirectUrl: string;
  remember:boolean;

  constructor(private router: Router,
              private activatedRoute: ActivatedRoute,
              private authenticationService: AuthenticationService,
              private userService: UserService,
              private _translate: TranslateService) {
    this.redirectUrl = this.activatedRoute.snapshot.queryParams['redirectTo'];
    this.remember = false;
  }

  ngOnInit(): void {
    this.userService.logout();
  }

  login() {
    this.loading = true;
    this.authenticationService.login(this.model.username, this.model.password)
      .subscribe(
        result => {
          this.loading = false;

          if (result) {
            this.userService.login(result,this.remember);
            this.navigateAfterSuccess();
          } else {
            this.error = this._translate.instant('Login incorrect');
          }
        },
        error => {
          this.error = this._translate.instant('Login incorrect');
          this.loading = false;
        }
      );
  }

  forgetMdp() {
    this.router.navigate(['/demandereinitmdp']);
  }

  private navigateAfterSuccess() {
    if (this.redirectUrl) {
      this.router.navigateByUrl(this.redirectUrl);
    } else {
      this.router.navigate(['/']);
    }
  }
}
