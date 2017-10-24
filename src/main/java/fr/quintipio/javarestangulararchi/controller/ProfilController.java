package fr.quintipio.javarestangulararchi.controller;


import fr.quintipio.javarestangulararchi.model.Profil;
import fr.quintipio.javarestangulararchi.service.MailService;
import fr.quintipio.javarestangulararchi.service.ProfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasAuthority('ADMIN_USER')")
public class ProfilController {

    private final Logger log = LoggerFactory.getLogger(ProfilController.class);

    @Autowired
    private ProfilService profilService;

    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    public ResponseEntity<List<Profil>> listeAllProfil() {
        List<Profil> profil = profilService.findAll();
        if (profil.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(profil);
    }

    @GetMapping(value = "/profil/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profil> getProfil(@PathVariable long id) {
        Profil profil = profilService.findById(id);
        if(profil == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            return ResponseEntity.ok(profil);
        }
    }

    @RequestMapping(value="/profil/search", method = RequestMethod.GET)
    public ResponseEntity<List<Profil>> rechercherProfil(@RequestParam("libelle") String recherche) {
        List<Profil> resultat = profilService.searchByLibelle(recherche);
        if(resultat == null || (resultat != null && resultat.isEmpty()) || recherche.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(resultat);
    }

    @DeleteMapping(value = "/profil/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        log.info("Effacement du profil d'id"+id);
        profilService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/profil")
    public ResponseEntity<Profil> createProfil(@RequestBody Profil profil) {
        this.profilService.save(profil);
        log.info("Crétion du profil"+profil.getLibelle()+" id:"+profil.getId());
        return ResponseEntity.ok(profil);
    }

    @PutMapping(value = "/profil")
    public ResponseEntity<Profil> updateprofil(@RequestBody Profil profil) {
        this.profilService.update(profil);
        log.info("Mise à jour du profil"+profil.getLibelle()+" id:"+profil.getId());
        return ResponseEntity.ok(profil);
    }


}
