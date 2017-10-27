package fr.quintipio.javarestangulararchi.repositories;

import fr.quintipio.javarestangulararchi.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Couche JPA pour les profils
 */
@Repository
public interface ProfilRepository extends JpaRepository<Profil,Long> {

    /**
     * Recherche un profil par son libelle
     * @param libelle le libelle à rechercher
     * @return la liste des profils trouvé
     */
    List<Profil> findByLibelleContaining(String libelle);
}
