import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {ListeProfilComponent} from "./component/profil/listeProfil.component";

const routes: Routes = [
  //{ path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'profils',  component: ListeProfilComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
