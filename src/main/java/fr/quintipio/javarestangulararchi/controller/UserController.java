package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

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
    public ResponseEntity<Utilisateur> createUser(@RequestBody Utilisateur user) {
        this.userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    //@PutMapping(value = "/utilisateur")
    public ResponseEntity<Utilisateur> updateUser(@RequestBody Utilisateur user) {
        this.userService.updateUser(user);
        return ResponseEntity.ok(user);
    }
}
