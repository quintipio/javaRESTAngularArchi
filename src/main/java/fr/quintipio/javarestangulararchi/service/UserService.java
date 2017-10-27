package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.model.Utilisateur;

import java.util.List;

public interface UserService {

    /**
     * Recherche un utilisateur par son id
     * @param id l'id de l'utilisateur à rechercher
     * @return l'utilisateur trouvé
     */
    Utilisateur findById(Long id);

    /**
     * Recherche une lsite d'utilisateur à partir du login
     * @param name le sso à rechercher
     * @return la liste des utilisateurs trouvé
     */
    List<Utilisateur> findBySsoLike(String name);

    /**
     * Trouve un utilisateur par son login
     * @param sso le login
     * @return l'utilisateur
     */
    Utilisateur findBySso(String sso);

    /**
     * Sauvegarde un utilisateur
     * @param utilisateur l'utilisateur
     * @return L'utilisateur sauvegardé
     */
    Utilisateur saveUser(Utilisateur utilisateur);

    /**
     * Met à jour un utilisateur
     * @param utilisateur l'utilisateur à mettre à jour
     * @return l'utilisatuer mis à jour
     */
    Utilisateur updateUser(Utilisateur utilisateur);

    /**
     * Efface un utilisateur
     * @param id l'id de l'utilisateur à effacer
     */
    void deleteUser(Long id);

    /**
     * Retourne tout les utilisateurs
     * @return la liste des utilisateurs
     */
    List<Utilisateur> findAll();

    /**
     * Vérifie qu'un lien d'activation n'existe pas déjà ne base
     * @param link le lien à chercher
     * @return true si déjà existant
     */
    Boolean linkActivationAlreadyExist(String link);

    /**
     * Retourne un utilisateur par son lien d'activation
     * @param link le lien
     * @return l'utilisateur
     */
    Utilisateur findByLinkActivation(String link);

    /**
     * Vérifie qu'un lien de reinit de mot de passe n'existe pas déjà ne base
     * @param link le lien à chercher
     * @return true si déjà existant
     */
    Boolean linkResetPasswordAlreadyExist(String link);

    /**
     * Retourne un utilisateur par son lien de reinit de mot de passe
     * @param link le lien
     * @return l'utilisateur
     */
    Utilisateur findByLinkResetPassword(String link);

    /**
     * Vérifie qu'un login n'est pas déjà présent en base
     * @param sso le login
     * @param id l'id de l'utilisateur à exclure sinon null
     * @return true si déjà existant
     */
    Boolean checkSsoUserExist(String sso, Long id);

    /**
     * Efface les utilisateurs dont les compte n'ont pas été activé depuis trois jours
     */
    void effacerUtilisateurNonActive();

}
