package fr.quintipio.javarestangulararchi.controller;

import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.service.UserService;
import fr.quintipio.javarestangulararchi.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/standard")
//@PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
public class StandardController {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/accueil", method = RequestMethod.GET)
    public ResponseEntity<String> getAccueil() {
        return ResponseEntity.ok("Bienvenue sur la page d'accueil du compte utilisateur");
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
            Utilisateur userupdate = userService.updateUser(userToUpdate);
            if(userupdate != null) {
                return ResponseEntity.ok(userupdate);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @RequestMapping(value="/updatePassword",method = RequestMethod.GET)
    public ResponseEntity<String> updatePassword(@RequestParam("oldPass") String oldPass,@RequestParam("newPass") String newPass) {
        String sso = SecurityUtils.getCurrentUserLogin();
        Utilisateur user = userService.findBySso(sso);
        if(user != null) {
            if(passwordEncoder.matches(oldPass,user.getMotDePasse())) {
                user.setMotDePasse(passwordEncoder.encode(newPass));
                userService.updateUser(user);
                return ResponseEntity.ok("ok");
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
