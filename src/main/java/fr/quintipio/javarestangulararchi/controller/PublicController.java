package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.service.MailService;
import fr.quintipio.javarestangulararchi.service.MessageByLocaleService;
import fr.quintipio.javarestangulararchi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controleur pour toute la partie publique du site (accesible sans compte)
 */
@RestController
@RequestMapping("/public")
@CrossOrigin("*")
public class PublicController {

    private final Logger log = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageByLocaleService messageByLocaleService;


    /**
     * Message pour la page d'accueil
     * @return
     */
    @RequestMapping(value = "/accueil", method = RequestMethod.GET)
    public ResponseEntity<String> getAccueil() {
        return ResponseEntity.ok(messageByLocaleService.getMessage("accueilPublic"));
    }


    /**
     * Service appelé pour demander une réinitialisation du mot de passe :
     * Recherche l'utilisateur par son identifiant, puis envoi un mail de réinitialisation du mot de passe
     * @param sso l'identifiant dont on souhaite réinitialiser le mot de passe
     * @return la requete HTTP Ok quqoiqu'il arrive (
     */
    @RequestMapping(value = "/demandeReinitMdp", method = RequestMethod.GET)
    public ResponseEntity demandeReinitMdp(@RequestParam("sso") String sso) {
        Utilisateur user = userService.findBySso(sso);
        log.info("Identifiant "+sso+" - Demande reinit Mot de passe");

        if(user != null) {
            String token = null;
            do {
                token = UUID.randomUUID().toString();
                log.debug(token);
            }while(userService.linkAlreadyExist(token));

            user.setLink(token);
            userService.updateUser(user);
            log.info("L'utilisateur "+sso+" id:"+user.getId()+" à demander la réinitialisation du mot de passe avec le token:"+token);

            if(!mailService.sendEmailFromTemplate(user,"passwordResetEmail",messageByLocaleService.getMessage("mail.titre.reinitMdp"))) {
                log.warn("L'utilisateur "+sso+" id:"+user.getId()+" à échouer sur l'envoi du mail:"+user.getEmail()+" pour réinitialiser son mot de passe");

                user.setLink(null);
                userService.updateUser(user);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    /**
     * Service pour reinitiliser le mot de passe si l'utiliseur est trouvé par son token unique et qu'il est actif
     * @param link le lien de l'utilisateur le token de l'utilisateur
     * @param newmdp le nouveau mot de passe de l'utilisateur à chiffrer et enregistrer
     * @return ok ou bad_request
     */
    @RequestMapping(value = "/reinitMdp", method = RequestMethod.GET)
    public ResponseEntity reinitMdp(@RequestParam("link") String link,@RequestParam("newmdp") String newmdp) {
        log.info("Le token "+link+" est lancé pour réinitialiser son mot de passe");
        Utilisateur user = userService.findByLink(link);
        if(user != null && user.getActive()) {
            String encodeMdp = passwordEncoder.encode(newmdp);
            user.setMotDePasse(encodeMdp);
            user.setLink(null);
            userService.updateUser(user);
            log.info("L'utilisateur id:"+user.getId()+" sso:"+user.getSso()+"  réinitialisé son mot de passe");
            return new ResponseEntity(HttpStatus.OK);
        }
        log.warn("Le token "+link+" à écohuer réinitialiser son mot de passe");
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }


    /**
     * Service pour activer un compte utilisateur
     * @param link le token de l'utilisateur à activer
     * @return ok ou bad_request
     */
    @RequestMapping(value = "/activerCompte", method = RequestMethod.GET)
    public ResponseEntity activerCompte(@RequestParam("link") String link) {
        Utilisateur user = userService.findByLink(link);
        log.info("Activation de l'utilisateur avec le token"+link);
        if(user != null) {
            user.setActive(true);
            user.setLink(null);
            userService.updateUser(user);
            log.info("Utilisateur id:"+user.getId()+" est activé");
            return new ResponseEntity(HttpStatus.OK);
        }

        log.warn("Echec de l'activation de l'utilisateur avec le token"+link);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }





}
