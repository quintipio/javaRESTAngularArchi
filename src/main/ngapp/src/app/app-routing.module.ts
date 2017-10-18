import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {ListeProfilComponent} from "./component/admin/profil/listeProfil.component";
import {EditProfilComponent} from "./component/admin/profil/editProfil.component";
<<<<<<< HEAD
import {PageUtilisateurComponent} from "./component/standard/pageUtilisateur.component";
import {PageAccueilComponent} from "./component/public/pageAccueil.component";
import {PageAdminComponent} from "./component/admin/pageAdmin.component";
import {AuthGuard} from "./guards/auth-guard.service";
import {ErrorConnexionComponent} from "./commons/errorConnexion.component";
import {AdminAuthGuard} from "./guards/admin-auth-guard.service";

const routes: Routes = [
  { path: 'erreurConnexion',component: ErrorConnexionComponent },

  { path: '', redirectTo: '/accueil', pathMatch: 'full' },
  { path: 'accueil',  component: PageAccueilComponent},

  { path: 'pageUtilisateur',  component: PageUtilisateurComponent, canActivate: [AuthGuard]},
  { path: 'pageAdmin',  component: PageAdminComponent, canActivate: [AuthGuard, AdminAuthGuard]},

  { path: 'profils',  component: ListeProfilComponent, canActivate: [AuthGuard, AdminAuthGuard] },
  { path: 'profils/edit',  component: EditProfilComponent, canActivate: [AuthGuard, AdminAuthGuard] },
=======
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
>>>>>>> 73519aefe53d99a6077033c5a32ad238e8dc729f
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
