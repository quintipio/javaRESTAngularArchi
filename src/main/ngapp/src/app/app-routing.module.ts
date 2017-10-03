import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {ListeProfilComponent} from "./component/admin/profil/listeProfil.component";
import {EditProfilComponent} from "./component/admin/profil/editProfil.component";
import {PageUtilisateurComponent} from "./component/accueil/pageUtilisateur.component";
import {PageAccueilComponent} from "./component/accueil/pageAccueil.component";
import {PageAdminComponent} from "./component/accueil/pageAdmin.component";

const routes: Routes = [
  { path: '', redirectTo: '/accueil', pathMatch: 'full' },
  { path: 'accueil',  component: PageAccueilComponent},
  { path: 'pageUtilisateur',  component: PageUtilisateurComponent},
  { path: 'pageAdmin',  component: PageAdminComponent},

  { path: 'profils',  component: ListeProfilComponent },
  { path: 'profils/edit',  component: EditProfilComponent },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
