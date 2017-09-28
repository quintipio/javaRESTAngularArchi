package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.model.Profil;

import java.util.List;

public interface ProfilService {

    Profil findById(Long id);

    List<Profil> findAll();

    void save(Profil profil);

    void update(Profil profil);

    void delete(Long id);

    List<Profil> searchByLibelle(String libelle);
}
