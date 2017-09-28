package fr.quintipio.javarestangulararchi.controller;


import fr.quintipio.javarestangulararchi.model.Profil;
import fr.quintipio.javarestangulararchi.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProfilController {

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
        System.out.println("ok : "+id);
        profilService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/profil")
    public ResponseEntity<Profil> createUser(@RequestBody Profil profil) {
        this.profilService.save(profil);
        return ResponseEntity.ok(profil);
    }

    @PutMapping(value = "/profil")
    public ResponseEntity<Profil> updateUser(@RequestBody Profil profil) {
        this.profilService.update(profil);
        return ResponseEntity.ok(profil);
    }


}
