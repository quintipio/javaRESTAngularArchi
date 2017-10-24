package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserDetailService implements UserDetailsService  {

    @Autowired
    private UtilisateurRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findBySso(s);

        if(user == null) {
            throw new UsernameNotFoundException(String.format("Utilisateur inconnu", s));
        }

        if(!user.getActive()) {
            throw new UsernameNotFoundException(String.format("Utilisateur désactivé", s));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getUserProfiles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getLibelle()));
        });

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(user.getSso(), user.getMotDePasse(),user.getActive(),true,true,true, authorities);

        return userDetails;
    }


}