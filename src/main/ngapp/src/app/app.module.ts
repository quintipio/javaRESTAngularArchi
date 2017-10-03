import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';

import { AppRoutingModule } from './app-routing.module';

import {ProfilService} from "./service/ProfilService";
import { AppComponent } from './app.component';
import {ListeProfilComponent} from "./component/admin/profil/listeProfil.component";
import {EditProfilComponent} from "./component/admin/profil/editProfil.component";
import {SearchprofilComponent} from "./component/admin/profil/searchprofil.component";
import {PageUtilisateurComponent} from "./component/accueil/pageUtilisateur.component";
import {PageAccueilComponent} from "./component/accueil/pageAccueil.component";
import {PageAdminComponent} from "./component/accueil/pageAdmin.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,

    PageAccueilComponent,
    PageUtilisateurComponent,
    PageAdminComponent,

    ListeProfilComponent,
    EditProfilComponent,
    SearchprofilComponent
  ],
  providers: [ProfilService],
  bootstrap: [AppComponent]
})
export class AppModule { }
