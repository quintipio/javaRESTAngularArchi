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
public class ProfilController {

    @Autowired
    private ProfilService profilService;

    @CrossOrigin
    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Profil>> listeAllProfil() {
        List<Profil> profil = profilService.findAll();
        if (profil.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(profil);
    }

    @CrossOrigin
    @GetMapping(value = "/profil/{id}", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Profil> getProfil(@PathVariable("id") Long id) {
        Profil profil = profilService.findById(id);
        if(profil == null) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        else {
            return ResponseEntity.ok(profil);
        }
    }
}
