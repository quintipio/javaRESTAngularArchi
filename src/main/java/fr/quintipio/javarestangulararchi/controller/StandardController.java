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

import java.util.Optional;

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

    @RequestMapping(value = "/accueil", method = RequestMethod.GET)
    public ResponseEntity<String> getAccueil() {
        return ResponseEntity.ok(messageByLocaleService.getMessage("accueilStandard"));
    }

    @RequestMapping(value="/updateUser", method = RequestMethod.GET)
    public ResponseEntity<Utilisateur> getInfoUser() {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            user.setId(0L);
            user.setMotDePasse(null);
            user.setUserProfiles(null);
            user.setActive(null);
            user.setLink(null);
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @RequestMapping(value="/updateUser",method = RequestMethod.PUT)
    public ResponseEntity<Utilisateur> updateUser(@RequestBody Utilisateur user) {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur userToUpdate = userService.findBySso(sso);
        if(userToUpdate != null) {
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setNom(user.getNom());
            userToUpdate.setPrenom(user.getPrenom());
            userToUpdate.setLangue(user.getLangue());
            Utilisateur userupdate = userService.updateUser(userToUpdate);
            if(userupdate != null) {
                log.info(user+" met à jour ses informations personnelles");
                return ResponseEntity.ok(userupdate);
            }
        }
        log.warn("echec de mises à jour des informations personnelles de "+sso);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value="/updateLangue", method = RequestMethod.GET)
    public ResponseEntity updateLangue(@RequestParam("langueUser") String langue) {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            user.setLangue(langue);
            userService.updateUser(user);
            log.info("L'utilisateur "+user.getSso()+" id:"+user.getId()+" change sa langue en"+langue);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value="/getLangue", method = RequestMethod.GET)
    public ResponseEntity getLangue() {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            return ResponseEntity.ok(user.getLangue());
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/updatePassword",method = RequestMethod.GET)
    public ResponseEntity<String> updatePassword(@RequestParam("oldPass") String oldPass,@RequestParam("newPass") String newPass) {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            if(passwordEncoder.matches(oldPass,user.getMotDePasse())) {
                user.setMotDePasse(passwordEncoder.encode(newPass));
                userService.updateUser(user);
                log.info(user+" met à jour son mot de passe");
                return ResponseEntity.ok("ok");
            } else {
                return ResponseEntity.ok("pok");
            }
        }
        log.warn("echec de mises à jour du mot de passe de "+sso);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/supprimerCompte", method = RequestMethod.DELETE)
    public ResponseEntity deleteCompte() {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            userService.deleteUser(user.getId());
        }
        return new ResponseEntity(HttpStatus.OK);
    }


}
