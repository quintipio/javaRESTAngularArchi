package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.configuration.Constantes;
import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.service.MessageByLocaleService;
import fr.quintipio.javarestangulararchi.service.UserService;
import fr.quintipio.javarestangulararchi.utils.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Controleurs général à tout les utilisateurs
 */
@RestController
@CrossOrigin("*")
@RequestMapping(Constantes.URL_USER)
//@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
public class StandardController {

    private final Logger log = LoggerFactory.getLogger(StandardController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageByLocaleService messageByLocaleService;

    /**
     * Message d'accueil de la page utilisateur
     * @return
     */
    @RequestMapping(value = "/accueil", method = RequestMethod.GET)
    public ResponseEntity<String> getAccueil() {
        return ResponseEntity.ok(messageByLocaleService.getMessage("accueilStandard"));
    }

    /**
     * Récupère les infos personnelles d'un utilisateur
     * @return
     */
    @RequestMapping(value="/updateUser", method = RequestMethod.GET)
    public ResponseEntity<Utilisateur> getInfoUser() {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            user.setId(0L);
            user.setMotDePasse(null);
            user.setUserProfiles(null);
            user.setActif(null);
            user.setLinkActivation(null);
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * Met à jour les infos personnelles d'un utilisateur
     * @param user les nouvelles infos de l'utilisateur
     * @return le nouvel utilisateur
     */
    @RequestMapping(value="/updateUser",method = RequestMethod.PUT)
    public ResponseEntity<Utilisateur> updateUser(@RequestBody Utilisateur user) {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur userToUpdate = userService.findBySso(sso);
        if(userToUpdate != null) {
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setNom(user.getNom());
            userToUpdate.setPrenom(user.getPrenom());
            userToUpdate.setLangue(user.getLangue());
            userToUpdate.setLastModifiedDate(new Date());
            Utilisateur userupdate = userService.updateUser(userToUpdate);
            if(userupdate != null) {
                log.info("Update des infos personnelles de "+user+" en "+userToUpdate);
                return ResponseEntity.ok(userupdate);
            }
        }
        log.warn("Echec update infos personnelles : "+sso);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Met à jour la langue d'un utilisateur
     * @param langue le diminutif de la nouvelle langue
     * @return
     */
    @RequestMapping(value="/updateLangue", method = RequestMethod.GET)
    public ResponseEntity updateLangue(@RequestParam("langueUser") String langue) {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            user.setLangue(langue);
            user.setLastModifiedDate(new Date());
            userService.updateUser(user);
            log.info("Changement de langues de : "+user);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Retourne la langue d'un utilisateur
     * @return
     */
    @RequestMapping(value="/getLangue", method = RequestMethod.GET)
    public ResponseEntity getLangue() {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            return ResponseEntity.ok(user.getLangue());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Met à jour le mot de passe d'un utilisateur
     * @param oldPass l'ancien mot de passe pour controle
     * @param newPass le nouveau mot de passe
     * @return ok si ok sinon pok (mauvais ancien mot de passe)
     */
    @RequestMapping(value="/updatePassword",method = RequestMethod.GET)
    public ResponseEntity<String> updatePassword(@RequestParam("oldPass") String oldPass,@RequestParam("newPass") String newPass) {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            if(passwordEncoder.matches(oldPass,user.getMotDePasse())) {
                user.setMotDePasse(passwordEncoder.encode(newPass));
                user.setLastModifiedDate(new Date());
                userService.updateUser(user);
                log.info("Update password : "+user);
                return ResponseEntity.ok("ok");
            } else {
                return ResponseEntity.ok("pok");
            }
        }
        log.warn("Echec update password de "+sso);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Supprime le compte d'un utilisateur
     * @return
     */
    @RequestMapping(value = "/supprimerCompte", method = RequestMethod.DELETE)
    public ResponseEntity deleteCompte() {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            userService.deleteUser(user.getId());
            log.info("Effacement du compte : "+user);
        }
        log.info("Echec - effacement du compte : "+sso);
        return new ResponseEntity(HttpStatus.OK);
    }


}
