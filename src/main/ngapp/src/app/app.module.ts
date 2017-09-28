import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';

import { AppRoutingModule } from './app-routing.module';

import {ProfilService} from "./service/ProfilService";
import { AppComponent } from './app.component';
import {ListeProfilComponent} from "./component/profil/listeProfil.component";
import {EditProfilComponent} from "./component/profil/editProfil.component";
import {SearchprofilComponent} from "./component/profil/searchprofil.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    ListeProfilComponent,
    EditProfilComponent,
    SearchprofilComponent
  ],
  providers: [ProfilService],
  bootstrap: [AppComponent]
})
export class AppModule { }
