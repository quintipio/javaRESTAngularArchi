import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {ListeProfilComponent} from "./component/admin/profil/listeProfil.component";
import {EditProfilComponent} from "./component/admin/profil/editProfil.component";
import {PageUtilisateurComponent} from "./component/standard/pageUtilisateur.component";
import {PageAccueilComponent} from "./component/public/pageAccueil.component";
import {PageAdminComponent} from "./component/admin/pageAdmin.component";
import {AuthGuard} from "./guards/auth-guard.service";
import {ConnexionComponent} from "./commons/connexion.component";
import {AdminAuthGuard} from "./guards/admin-auth-guard.service";
import {ModifCompteComponent} from './component/standard/modifCompte.component';
import {PageDemandeReinitMdpComponent} from './component/public/pageDemandeReinitmdp.component';
import {PageReinitMdpComponent} from './component/public/pageReinitMdp.component';
import {PageActivationCompteComponent} from './component/public/pageActivationCompte.component';
import {GererUserComponent} from "./component/admin/user/gererUser.component";
import {EditUserComponent} from "./component/admin/user/editUser.component";

const routes: Routes = [
  { path: 'connexion',component: ConnexionComponent },

  { path: '', redirectTo: '/accueil', pathMatch: 'full' },
  { path: 'accueil',  component: PageAccueilComponent},
  { path: 'demandereinitmdp',  component: PageDemandeReinitMdpComponent},
  { path: 'reinitmdp',  component: PageReinitMdpComponent },
  { path: 'activerCompte',  component: PageActivationCompteComponent },

  { path: 'pageUtilisateur',  component: PageUtilisateurComponent, canActivate: [AuthGuard]},
  { path: 'monCompte',  component: ModifCompteComponent, canActivate: [AuthGuard]},

  { path: 'pageAdmin',  component: PageAdminComponent, canActivate: [AuthGuard, AdminAuthGuard]},
  { path: 'profils',  component: ListeProfilComponent, canActivate: [AuthGuard, AdminAuthGuard] },
  { path: 'profils/edit',  component: EditProfilComponent, canActivate: [AuthGuard, AdminAuthGuard] },
  { path: 'users',  component: GererUserComponent, canActivate: [AuthGuard, AdminAuthGuard] },
  { path: 'user/edit',  component: EditUserComponent, canActivate: [AuthGuard, AdminAuthGuard] },
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
