package fr.quintipio.javarestangulararchi.service;

import fr.quintipio.javarestangulararchi.model.Utilisateur;

import java.util.List;

public interface UserService {

    Utilisateur findById(Long id);

    Utilisateur findBySso(String name);

    void saveUser(Utilisateur utilisateur);

    void updateUser(Utilisateur utilisateur);

    void deleteUser(Long id);

    List<Utilisateur> findAll();
}
