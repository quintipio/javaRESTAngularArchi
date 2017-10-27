package fr.quintipio.javarestangulararchi.controller;


import fr.quintipio.javarestangulararchi.configuration.Constantes;
import fr.quintipio.javarestangulararchi.model.Profil;
import fr.quintipio.javarestangulararchi.service.ProfilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controleur pour la gestion des profils
 */
@RestController
@RequestMapping(Constantes.URL_ADMIN)
//@PreAuthorize("hasAuthority('ADMIN_USER')")
public class ProfilController {

    private final Logger log = LoggerFactory.getLogger(ProfilController.class);

    @Autowired
    private ProfilService profilService;

    /**
     * Listing de tout les profils
     * @return liste des profils
     */
    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    public ResponseEntity<List<Profil>> listeAllProfil() {
        List<Profil> profil = profilService.findAll();
        if (profil.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(profil);
    }

    /**
     * Retourne un profil
     * @param id l'id du profil souhaité
     * @return le profil
     */
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

    /**
     * recherche plusieurs profils en fonction de leurs libéllé
     * @param recherche la chaine à rechercher dans les libellés
     * @return les profils trouvés
     */
    @RequestMapping(value="/profil/search", method = RequestMethod.GET)
    public ResponseEntity<List<Profil>> rechercherProfil(@RequestParam("libelle") String recherche) {
        List<Profil> resultat = profilService.searchByLibelle(recherche);
        if(resultat == null || (resultat != null && resultat.isEmpty()) || recherche.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(resultat);
    }

    /**
     * Efface un profil
     * @param id l'id du profil à effacer
     * @return
     */
    @DeleteMapping(value = "/profil/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        log.info("Effacement profil id : "+id);
        profilService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * Créer un profil
     * @param profil le profil à créer
     * @return le profil crée
     */
    @PostMapping(value = "/profil")
    public ResponseEntity<Profil> createProfil(@Valid @RequestBody Profil profil) {
        this.profilService.save(profil);
        log.info("Crétion du profil : "+profil);
        return ResponseEntity.ok(profil);
    }

    /**
     * Met à jour le libellé d'un profil
     * @param profil le nouveau profil
     * @return le nouveau profil
     */
    @PutMapping(value = "/profil")
    public ResponseEntity<Profil> updateprofil(@Valid @RequestBody Profil profil) {
        this.profilService.update(profil);
        log.info("Mise à jour du profil : "+profil);
        return ResponseEntity.ok(profil);
    }


}
