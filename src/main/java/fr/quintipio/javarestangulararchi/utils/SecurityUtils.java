package fr.quintipio.javarestangulararchi.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Random;


public final class SecurityUtils {


    private static final char[] listeLettreMinuscule = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'
            , 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] listeLettreMajuscule = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K'
            , 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] listeChiffre = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    private static final char[] listeCaractereSpeciaux = { '²', '&', 'é', '"', '#', '\'', '{', '-', '|', 'è', '_', '\\', 'ç', 'à', '@', ')', '(', '[', ']', '=', '+', '}', '£', '$', '¤', '%', 'ù', 'µ', '*', '?', ',', '.', ';', '/', ':', '§', '!', '€', '>', '<'};


    private SecurityUtils() {
    }

    /**
     * Retourne le login de l'utilisateur courant connecté
     *
     * @return le login
     */
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName = null;
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }
        return userName;
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user
     */
    public static String getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getCredentials() instanceof String) {
            return (String) authentication.getCredentials();
        }
        return null;
    }

    /**
     * Genere un mot de passe aléatoire composer de caractères majuscules, minuscules, de chiffres et de caractères spéciaux
     * @param longueur longueur du mot de passe souhaité, si 0 sera de 12 caractères
     * @param lettre autorise les lettres minuscules et majuscules dans le mot de passe
     * @param chiffre autorise les chiffres dans le mot de passe
     * @param caracSpeciaux autorise les caractères spéciaux dans le mot de passe
     * @return le mot de passe généré
     */
    public static String genereMotdePasse(int longueur, boolean lettre, boolean chiffre, boolean caracSpeciaux){
        int length = (longueur == 0) ? 12 : longueur;
        String password = "";
        Random rnd = new Random();
        for (int i = 0; i < length; i++)
        {
            boolean caracBienCree = false;
            do
            {
                int typeTab = rnd.nextInt(4);
                switch (typeTab)
                {
                    case 0:
                        if (lettre)
                        {
                            password += listeLettreMinuscule[rnd.nextInt(listeLettreMinuscule.length)];
                            caracBienCree = true;
                        }
                        break;
                    case 1:
                        if (lettre)
                        {
                            password += listeLettreMajuscule[rnd.nextInt(listeLettreMajuscule.length)];
                            caracBienCree = true;
                        }
                        break;
                    case 2:
                        if (chiffre)
                        {
                            password += listeChiffre[rnd.nextInt(listeChiffre.length)];
                            caracBienCree = true;
                        }
                        break;
                    case 3:
                        if (caracSpeciaux)
                        {
                            password += listeCaractereSpeciaux[rnd.nextInt(listeCaractereSpeciaux.length)];
                            caracBienCree = true;
                        }
                        break;
                }
            } while (!caracBienCree);
        }
        return password;
    }

}
