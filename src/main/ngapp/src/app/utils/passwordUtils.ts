export class PasswordUtils {

   public static checkpassword(mdpA: string, mdpB:string) :string {
    if(mdpA===mdpB) {
        if(mdpA.length < 6) {
          return "Votre mot de passe doit faire au minimum 6 caractères";
        }
    }
    else {
      return "Vos deux mots de passe sont différents";
    }
  }
}
