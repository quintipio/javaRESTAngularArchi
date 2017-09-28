import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {ListeProfilComponent} from "./component/profil/listeProfil.component";
import {EditProfilComponent} from "./component/profil/editProfil.component";

const routes: Routes = [
  //{ path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'profils',  component: ListeProfilComponent },
  { path: 'profils/edit',  component: EditProfilComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
