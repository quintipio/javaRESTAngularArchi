import {ErrorHandler, NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import {Http, HttpModule} from '@angular/http';

import { AppRoutingModule } from './app-routing.module';

import {ProfilService} from "./service/ProfilService";
import { AppComponent } from './app.component';
import {ListeProfilComponent} from "./component/admin/profil/listeProfil.component";
import {EditProfilComponent} from "./component/admin/profil/editProfil.component";
import {SearchprofilComponent} from "./component/admin/profil/searchprofil.component";
import {PageUtilisateurComponent} from "./component/standard/pageUtilisateur.component";
import {PageAccueilComponent} from "./component/public/pageAccueil.component";
import {PageAdminComponent} from "./component/admin/pageAdmin.component";
import {LoginComponent} from "./component/login/login.component";
import {UserService} from "./service/user.service";
import {AuthenticationService} from "./service/authentication.service";
import {TOKEN_NAME} from "./constantes";
import {AuthConfig, AuthHttp} from "angular2-jwt";
import {AdminAuthGuard} from "./guards/admin-auth-guard.service";
import {AuthGuard} from "./guards/auth-guard.service";
import {PublicService} from "./service/PublicService";
import {AdminService} from "./service/AdminService";
import {StandardService} from "./service/StandardService";
import {ConnexionComponent} from "./commons/connexion.component";
import {ModifCompteComponent} from './component/standard/modifCompte.component';
import {PageDemandeReinitMdpComponent} from './component/public/pageDemandeReinitmdp.component';
import {PageReinitMdpComponent} from './component/public/pageReinitMdp.component';
import {PageActivationCompteComponent} from './component/public/pageActivationCompte.component';
import {GlobalErrorHandler} from './service/GlobalErrorHandler';
import {ErrorComponent} from './commons/error.component';
import {CommunicationErrorService} from './service/CommunicationErrorService';
import {PasswordStrengthBarComponent} from './component/tools/password-strength-bar.component';
import {TranslatePipe} from './translate/translate.pipe';
import {TRANSLATION_PROVIDERS} from './translate/translation';
import {TranslateService} from './translate/translate.service';

export function authHttpServiceFactory(http: Http) {
  return new AuthHttp(new AuthConfig({
    headerPrefix: 'Bearer',
    tokenName: TOKEN_NAME,
    globalHeaders: [{'Content-Type': 'application/json'}],
    noJwtError: false,
    noTokenScheme: true,
    tokenGetter: (() => sessionStorage.getItem(TOKEN_NAME))
  }), http);
}

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  declarations: [
    TranslatePipe,
    PasswordStrengthBarComponent,

    AppComponent,
    ConnexionComponent,
    ErrorComponent,

    PageAccueilComponent,
    LoginComponent,
    PageDemandeReinitMdpComponent,
    PageReinitMdpComponent,
    PageActivationCompteComponent,

    PageUtilisateurComponent,
    ModifCompteComponent,

    PageAdminComponent,
    ListeProfilComponent,
    EditProfilComponent,
    SearchprofilComponent,

  ],
  providers: [
    {provide: AuthHttp, useFactory: authHttpServiceFactory, deps: [Http]},
    {provide: ErrorHandler, useClass: GlobalErrorHandler},
    TRANSLATION_PROVIDERS, TranslateService,
    CommunicationErrorService,
    ProfilService,
    AuthenticationService,
    UserService,
    PublicService,
    AdminService,
    StandardService,
    AuthGuard,
    AdminAuthGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
