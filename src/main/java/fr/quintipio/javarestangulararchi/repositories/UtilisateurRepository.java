package fr.quintipio.javarestangulararchi.repositories;


import fr.quintipio.javarestangulararchi.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    Utilisateur findBySsoLike(String sso);


}
