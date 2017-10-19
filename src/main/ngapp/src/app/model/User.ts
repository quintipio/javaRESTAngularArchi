import {Profil} from "./Profil";

export class User {
  id:number;
  sso:string;
  email:string;
  password:string;
  nom:string;
  prenom:string;
  profils:Profil[];
  active:boolean;
}
