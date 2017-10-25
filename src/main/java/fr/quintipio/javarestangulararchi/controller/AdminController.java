package fr.quintipio.javarestangulararchi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
//@PreAuthorize("hasAuthority('ADMIN_USER')")
public class AdminController {


    @RequestMapping(value = "/accueil", method = RequestMethod.GET)
    public ResponseEntity<String> getAccueil() {
        return ResponseEntity.ok("Bienvenue sur la page d'accueil du compte Admin");
    }
}
