package fr.quintipio.javarestangulararchi.repositories;


import fr.quintipio.javarestangulararchi.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    List<Utilisateur> findBySsoLike(String sso);

    Utilisateur findBySso(String sso);

    Integer countUtilisateurByLink(String link);

    Utilisateur findUtilisateurByLink(String link);

}
