package fr.quintipio.javarestangulararchi.service;


import fr.quintipio.javarestangulararchi.model.Profil;
import fr.quintipio.javarestangulararchi.repositories.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("profilService")
@Transactional
public class ProfilServiceImpl implements ProfilService {

    @Autowired
    private ProfilRepository profilRepository;

    /**
     * Recherche un profil par id
     * @param id l'id à rechercher
     * @return le profil
     */
    @Override
    public Profil findById(Long id) {
        Optional<Profil> profil = profilRepository.findById(id);
        return profil.isPresent()?profil.get():null;
    }

    /**
     * Retourne tout les profils
     * @return la liste des profils
     */
    @Override
    public List<Profil> findAll() {
        return profilRepository.findAll();
    }

    /**
     * sauvegarde un profil en base
     * @param profil le profil à sauvegarder
     */
    @Override
    public void save(Profil profil) {
        profilRepository.save(profil);
    }

    /**
     * Met à jour un profil en base
     * @param profil e profil avec les infos à jour
     */
    @Override
    public void update(Profil profil) {
        profilRepository.save(profil);
    }

    /**
     * efface un profil
     * @param id l'id du profil à effacer
     */
    @Override
    public void delete(Long id) {
        profilRepository.deleteById(id);
    }

    /**
     * recherche une liste de profils par son libellé
     * @param libelle le libelle à rechercher
     * @return la liste des profils trouvé
     */
    @Override
    public List<Profil> searchByLibelle(String libelle) {
       return profilRepository.findByLibelleContaining(libelle.toUpperCase());
    }
}
