import {Profil} from "./Profil";

export class User {
  id:number;
  sso:string;
  email:string;
  motDePasse:string;
  nom:string;
  prenom:string;
  userProfiles:Profil[];
  isActive:boolean;
  langue:string;
}
