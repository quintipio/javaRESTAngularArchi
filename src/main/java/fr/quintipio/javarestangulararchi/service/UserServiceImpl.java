package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("utilisateurService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * Recherche un utilisateur par son id
     * @param id l'id de l'utilisateur à rechercher
     * @return l'utilisateur trouvé
     */
    @Override
    public Utilisateur findById(Long id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return utilisateur.isPresent()?utilisateur.get():null;
    }

    /**
     * Recherche une lsite d'utilisateur à partir du login
     * @param name le sso à rechercher
     * @return la liste des utilisateurs trouvé
     */
    @Override
    public List<Utilisateur> findBySsoLike(String name) {
        return utilisateurRepository.findBySsoContaining(name);
    }

    /**
     * Trouve un utilisateur par son login
     * @param sso le login
     * @return l'utilisateur
     */
    @Override
    public Utilisateur findBySso(String sso) {
        return utilisateurRepository.findBySso(sso);
    }

    /**
     * Sauvegarde un utilisateur
     * @param utilisateur l'utilisateur
     * @return L'utilisateur sauvegardé
     */
    @Override
    public Utilisateur saveUser(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Met à jour un utilisateur
     * @param utilisateur l'utilisateur à mettre à jour
     * @return l'utilisatuer mis à jour
     */
    @Override
    public Utilisateur updateUser(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    /**
     * Efface un utilisateur
     * @param id l'id de l'utilisateur à effacer
     */
    @Override
    public void deleteUser(Long id) {
        utilisateurRepository.deleteById(id);
    }

    /**
     * Retourne tout les utilisateurs
     * @return la liste des utilisateurs
     */
    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    /**
     * Vérifie qu'un lien d'activation n'existe pas déjà ne base
     * @param link le lien à chercher
     * @return true si déjà existant
     */
    @Override
    public Boolean linkActivationAlreadyExist(String link) {
        Integer res = utilisateurRepository.countUtilisateurByLinkActivation(link);
        return res > 0;
    }

    /**
     * Retourne un utilisateur par son lien d'activation
     * @param link le lien
     * @return l'utilisateur
     */
    @Override
    public Utilisateur findByLinkActivation(String link) {
        return utilisateurRepository.findUtilisateurByLinkActivation(link);
    }

    /**
     * Vérifie qu'un lien de reinit de mot de passe n'existe pas déjà ne base
     * @param link le lien à chercher
     * @return true si déjà existant
     */
    @Override
    public Boolean linkResetPasswordAlreadyExist(String link) {
        Integer res = utilisateurRepository.countUtilisateurByLinkResetPassword(link);
        return res > 0;
    }

    /**
     * Retourne un utilisateur par son lien de reinit de mot de passe
     * @param link le lien
     * @return l'utilisateur
     */
    @Override
    public Utilisateur findByLinkResetPassword(String link) {
        return utilisateurRepository.findUtilisateurByLinkResetPassword(link);
    }

    /**
     * Vérifie qu'un login n'est pas déjà présent en base
     * @param sso le login
     * @param id l'id de l'utilisateur à exclure sinon null
     * @return true si déjà existant
     */
    @Override
    public Boolean checkSsoUserExist(String sso, Long id) {
        if(id != null) {
            return utilisateurRepository.countUtilisateursBySsoAndIdNot(sso,id) > 0;
        }else {
            return utilisateurRepository.countUtilisateursBySso(sso) > 0;
        }
    }

    /**
     * Efface les utilisateurs dont les compte n'ont pas été activé depuis trois jours
     */
    @Override
    public void effacerUtilisateurNonActive() {
        Date oldDate = Date.from(new Date().toInstant().minus(3, ChronoUnit.DAYS));
        Calendar cal = Calendar.getInstance();
        cal.setTime(oldDate);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        utilisateurRepository.deleteUtilisateursByActifIsFalseAndLinkActivationIsNotNullAndCreatedDateIsBefore( cal.getTime());
    }


}
