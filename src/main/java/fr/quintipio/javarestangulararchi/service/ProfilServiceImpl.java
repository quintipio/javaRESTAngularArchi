package fr.quintipio.javarestangulararchi.service;


import fr.quintipio.javarestangulararchi.model.Profil;
import fr.quintipio.javarestangulararchi.repositories.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("profilService")
@Transactional
public class ProfilServiceImpl implements ProfilService {

    @Autowired
    private ProfilRepository profilRepository;

    @Override
    public Profil findById(Long id) {
        return profilRepository.findOne(id);
    }

    @Override
    public List<Profil> findAll() {
        return profilRepository.findAll();
    }

    @Override
    public void save(Profil profil) {
        profilRepository.save(profil);
    }

    @Override
    public void update(Profil profil) {
        profilRepository.save(profil);
    }

    @Override
    public void delete(Long id) {
        profilRepository.delete(id);
    }

    @Override
    public List<Profil> searchByLibelle(String libelle) {
       return profilRepository.findByLibelleContaining(libelle.toUpperCase());
    }
}
