package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.configuration.Constantes;
import fr.quintipio.javarestangulararchi.service.MessageByLocaleService;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controleur générale pour un administrateur
 */
@RestController
@CrossOrigin("*")
@RequestMapping(Constantes.URL_ADMIN)
//@PreAuthorize("hasAuthority('ADMIN_USER')")
public class AdminController {

    @Autowired
    private MessageByLocaleService messageByLocaleService;

    /**
     * Un message d'accueil pour l'administrateur
     * @return
     */
    @RequestMapping(value = "/accueil", method = RequestMethod.GET)
    public ResponseEntity<String> getAccueil() {
        return ResponseEntity.ok(messageByLocaleService.getMessage("accueilAdmin"));
    }
}
