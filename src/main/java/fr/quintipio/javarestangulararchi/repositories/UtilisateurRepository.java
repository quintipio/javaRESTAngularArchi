package fr.quintipio.javarestangulararchi.repositories;


import fr.quintipio.javarestangulararchi.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Couche JPA pour les utilisateurs
 */
@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    /**
     * Recherche les utilisateurs par leurs login
     * @param sso le login à rechercher
     * @return la liste des utilisateurs trouvé
     */
    List<Utilisateur> findBySsoContaining(String sso);

    /**
     *
     * @param sso recherche un utilisateur par son login
     * @return l'utilisateur trouvé
     */
    Utilisateur findBySso(String sso);

    /**
     * Compte les utilisateurs possédant un loginen excluant un id
     * @param sso le login à trouver
     * @param id l'id de l'utilisateur à exclure
     * @return le nombre d'utilisateur trouvé
     */
    Integer countUtilisateursBySsoAndIdNot(String sso,Long id);

    /**
     * Compte les utilisateurs possédant un login
     * @param sso le login à trouver
     * @return le nombre d'utilisateur trouvé
     */
    Integer countUtilisateursBySso(String sso);

    /**
     * Compte le nombre de lien d'activation identique présent
     * @param linkActivation le lien d'activation du compte à rechercher
     * @return le nombre de résultat trouvé
     */
    Integer countUtilisateurByLinkActivation(String linkActivation);

    /**
     * Recherche un utilisateur par son lien d'activation
     * @param linkActivation le lien à rechercher
     * @return l'utilisateur trouvé
     */
    Utilisateur findUtilisateurByLinkActivation(String linkActivation);

    /**
     * Compte le nombre de lien de reinit de mot de passe identique présent
     * @param linkResetPassword le lien de reinit de mot de passe du compte à rechercher
     * @return le nombre de résultat trouvé
     */
    Integer countUtilisateurByLinkResetPassword(String linkResetPassword);

    /**
     * Recherche un utilisateur par son lien de reinit de mot de passe
     * @param linkResetPassword le lien à rechercher
     * @return l'utilisateur trouvé
     */
    Utilisateur findUtilisateurByLinkResetPassword(String linkResetPassword);

    /**
     * Efface tout les utilisateurs n'ayant pas été activé (activé=false & link activation != null) après trois jours(date de creation)
     * @param createdDate la date d'il y a trois jours
     */
    void deleteUtilisateursByIsActiveIsFalseAndLinkActivationIsNotNullAndCreatedDateIsBefore(Date createdDate);

}
