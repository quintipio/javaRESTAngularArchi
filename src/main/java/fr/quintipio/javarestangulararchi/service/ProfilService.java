package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.model.Profil;

import java.util.List;

public interface ProfilService {

    /**
     * Recherche un profil par id
     * @param id l'id à rechercher
     * @return le profil
     */
    Profil findById(Long id);

    /**
     * Retourne tout les profils
     * @return la liste des profils
     */
    List<Profil> findAll();

    /**
     * sauvegarde un profil en base
     * @param profil le profil à sauvegarder
     */
    void save(Profil profil);

    /**
     * Met à jour un profil en base
     * @param profil e profil avec les infos à jour
     */
    void update(Profil profil);

    /**
     * efface un profil
     * @param id l'id du profil à effacer
     */
    void delete(Long id);

    /**
     * recherche une liste de profils par son libellé
     * @param libelle le libelle à rechercher
     * @return la liste des profils trouvé
     */
    List<Profil> searchByLibelle(String libelle);
}
