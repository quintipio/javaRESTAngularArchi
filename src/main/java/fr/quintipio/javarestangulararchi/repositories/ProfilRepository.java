package fr.quintipio.javarestangulararchi.repositories;

import fr.quintipio.javarestangulararchi.model.Profil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilRepository extends JpaRepository<Profil,Long> {

}
