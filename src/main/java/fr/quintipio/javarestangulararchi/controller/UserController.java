package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.configuration.Constantes;
import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.service.MailService;
import fr.quintipio.javarestangulararchi.service.MessageByLocaleService;
import fr.quintipio.javarestangulararchi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Constantes.URL_ADMIN)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private MessageByLocaleService messageByLocaleService;

    @RequestMapping(value = "/utilisateur", method = RequestMethod.GET)
    public ResponseEntity<List<Utilisateur>> getAllUtilisteur() {
        List<Utilisateur> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/utilisateur/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> getProfil(@PathVariable long id) {
        Utilisateur utilisateur = userService.findById(id);
        if(utilisateur == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            return ResponseEntity.ok(utilisateur);
        }
    }

    @RequestMapping(value="/utilisateur/search", method = RequestMethod.GET)
    public ResponseEntity<List<Utilisateur>> rechercherProfil(@RequestParam("sso") String recherche) {
        List<Utilisateur> resultat = userService.findBySsoLike(recherche);
        if(resultat == null || (resultat != null && resultat.isEmpty()) || recherche.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(resultat);
    }

    @DeleteMapping(value = "/utilisateur/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/utilisateur")
    public ResponseEntity<Utilisateur> createUser(@Valid @RequestBody Utilisateur user) {
        user.setMotDePasse(passwordEncoder.encode(user.getMotDePasse()));
        if(!user.getActive()) {
            user.setLink(UUID.randomUUID().toString());
        }
        user = this.userService.saveUser(user);

        if(!user.getActive()) {
            mailService.sendEmailFromTemplate(user,Constantes.TEMPLATE_ACTIVATION,messageByLocaleService.getMessage("mail.titre.actverCompte"));
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping(value = "/utilisateur")
    public ResponseEntity<Utilisateur> updateUser(@Valid @RequestBody Utilisateur user) {
        Utilisateur newUser = userService.findById(user.getId());
        if(!userService.checkSsoUserExist(user.getSso(),user.getId())) {
            newUser.setSso(user.getSso());
            newUser.setPrenom(user.getPrenom());
            newUser.setNom(user.getNom());
            newUser.setEmail(user.getEmail());
            newUser.setUserProfiles(user.getUserProfiles());
            this.userService.updateUser(newUser);
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/utilisateur/activer/{id}")
    public ResponseEntity activerUser(@PathVariable long id) {
        changerActivationUser(true,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/utilisateur/desactiver/{id}")
    public ResponseEntity desactiverUser(@PathVariable long id) {
        changerActivationUser(false,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void changerActivationUser(boolean newEtat, long id) {
        Utilisateur user = userService.findById(id);
        user.setActive(newEtat);
        user.setLink(null);
        userService.updateUser(user);
    }

    @GetMapping(value = "/utilisateur/checkSso")
    public ResponseEntity<Boolean> checkSsoUserExist(@RequestParam("id") Long id, @RequestParam("sso") String recherche) {
        return ResponseEntity.ok(userService.checkSsoUserExist(recherche,id));
    }

    @GetMapping(value = "/utilisateur/updatePassword")
    public ResponseEntity updatePasswordUser(@RequestParam("id") Long id, @RequestParam("mdp") String mdp) {
        Utilisateur user = userService.findById(id);
        if(user != null) {
            user.setMotDePasse(passwordEncoder.encode(mdp));
            userService.updateUser(user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
