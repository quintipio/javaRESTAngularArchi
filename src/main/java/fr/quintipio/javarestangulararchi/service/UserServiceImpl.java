package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.model.Utilisateur;
import fr.quintipio.javarestangulararchi.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("utilisateurService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    @Override
    public Utilisateur findById(Long id) {
        return utilisateurRepository.findOne(id);
    }

    @Override
    public Utilisateur findBySso(String sso) {
        return utilisateurRepository.findBySsoLike(sso);
    }

    @Override
    public void saveUser(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void updateUser(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public void deleteUser(Long id) {
        utilisateurRepository.delete(id);
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }
}