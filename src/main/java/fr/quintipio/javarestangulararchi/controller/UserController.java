package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.configuration.Constantes;
import fr.quintipio.javarestangulararchi.model.AbstractEntity;
import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.service.MailService;
import fr.quintipio.javarestangulararchi.service.MessageByLocaleService;
import fr.quintipio.javarestangulararchi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Controleur pour la gestion des utilisateurs
 */
@RestController
@RequestMapping(Constantes.URL_ADMIN)
public class UserController extends AbstractEntity {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageByLocaleService messageByLocaleService;

    /**
     * Retourne tout les utilisateurs
     * @return
     */
    @RequestMapping(value = "/utilisateur", method = RequestMethod.GET)
    public ResponseEntity<List<Utilisateur>> getAllUtilisteur() {
        List<Utilisateur> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }

    /**
     * Retourne un utilisateur à partir de son id
     * @param id l'id de l'utilisateur
     * @return l'utilisateur
     */
    @GetMapping(value = "/utilisateur/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable long id) {
        Utilisateur utilisateur = userService.findById(id);
        if(utilisateur == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            return ResponseEntity.ok(utilisateur);
        }
    }

    /**
     * Recherche une liste d'utilisateur à partir du login
     * @param recherche le login à rechercher
     * @return la liste des utilisateurs trouvé
     */
    @RequestMapping(value="/utilisateur/search", method = RequestMethod.GET)
    public ResponseEntity<List<Utilisateur>> rechercherUtilisateur(@RequestParam("sso") String recherche) {
        List<Utilisateur> resultat = userService.findBySsoLike(recherche);
        if(resultat == null || (resultat != null && resultat.isEmpty()) || recherche.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(resultat);
    }

    /**
     * Efface un utilisateur de la base
     * @param id l'id de l'utilisateur
     * @return
     */
    @DeleteMapping(value = "/utilisateur/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.deleteUser(id);
        log.info("Effacement de l'utilisateur d'id : "+id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Créer un utilisateur et envoi un mail de demande d'activation si activation est à false
     * @param user l'utilisater à créer
     * @return l'utilisateur crée
     */
    @PostMapping(value = "/utilisateur")
    public ResponseEntity<Utilisateur> createUser(@Valid @RequestBody Utilisateur user) {
        if(!userService.checkSsoUserExist(user.getSso(),null)) {
            user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
            if(!user.getActive()) {
                user.setLinkActivation(UUID.randomUUID().toString());
            }
            user = this.userService.saveUser(user);

            if(!user.getActive()) {
                if(!mailService.sendEmailFromTemplate(user,Constantes.TEMPLATE_ACTIVATION,messageByLocaleService.getMessage("mail.titre.actverCompte"))) {
                    this.userService.deleteUser(user.getId());
                    log.warn("Echec de l'envoi du mail à : "+user);
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
            log.info("Création de l'utilisateur : "+user);
            return ResponseEntity.ok(user);
        }else {
            log.warn("Echec de la création de : "+user);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Met à jour les informations d'un utilisateur
     * @param user l'utilisateur à mettre à jour
     * @return l'utilisateur mis à jour
     */
    @PutMapping(value = "/utilisateur")
    public ResponseEntity<Utilisateur> updateUser(@Valid @RequestBody Utilisateur user) {
        Utilisateur newUser = userService.findById(user.getId());
        if(!userService.checkSsoUserExist(user.getSso(),user.getId())) {
            newUser.setSso(user.getSso());
            newUser.setPrenom(user.getPrenom());
            newUser.setNom(user.getNom());
            newUser.setEmail(user.getEmail());
            newUser.setUserProfiles(user.getUserProfiles());
            newUser.setLangue(user.getLangue());
            newUser.setLastModifiedDate(new Date());
            this.userService.updateUser(newUser);
            log.info("Update de l'utilisateur de : "+user);
        }
        return ResponseEntity.ok(user);
    }

    /**
     * Active un utilisateur
     * @param id l'id de l'utilisateur
     * @return
     */
    @GetMapping(value = "/utilisateur/activer/{id}")
    public ResponseEntity activerUser(@PathVariable long id) {
        changerActivationUser(true,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Désactive un utilisateur
     * @param id l'id de l'utilisateur
     * @return
     */
    @GetMapping(value = "/utilisateur/desactiver/{id}")
    public ResponseEntity desactiverUser(@PathVariable long id) {
        changerActivationUser(false,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Change l'état d'activation d'un utilisateur
     * @param newEtat le nouvel état de l'utilisateur
     * @param id l'id de l'utilisateur
     */
    private void changerActivationUser(boolean newEtat, long id) {
        Utilisateur user = userService.findById(id);
        user.setActive(newEtat);
        user.setLinkActivation(null);
        user.setLastModifiedDate(new Date());
        log.info(((newEtat)?"Activation de ":"Désactivation de ")+user);
        userService.updateUser(user);
    }

    /**
     * Vérifie que le login d'un utilisateur n'existe pas déjà en base
     * @param id l'id de l'utilisateur à exclure (sinon null)
     * @param recherche le login à rehcercher
     * @return true si déjà existant
     */
    @GetMapping(value = "/utilisateur/checkSso")
    public ResponseEntity<Boolean> checkSsoUserExist(@RequestParam("id") Long id, @RequestParam("sso") String recherche) {
        return ResponseEntity.ok(userService.checkSsoUserExist(recherche,id));
    }

    /**
     * Met à jour le mot de passe d'un utilisateur
     * @param id l'id de l'utilisateur
     * @param mdp le nouveau mot de passe
     * @return
     */
    @GetMapping(value = "/utilisateur/updatePassword")
    public ResponseEntity updatePasswordUser(@RequestParam("id") Long id, @RequestParam("mdp") String mdp) {
        Utilisateur user = userService.findById(id);
        if(user != null) {
            user.setMotDePasse(passwordEncoder.encode(mdp));
            user.setLastModifiedDate(new Date());
            userService.updateUser(user);
            log.info("Update du mot de passe de "+user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
