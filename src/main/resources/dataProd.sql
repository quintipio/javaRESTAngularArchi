INSERT INTO profil (`id`, `libelle`) VALUES ('1', 'STANDARD_USER'), ('2', 'ADMIN_USER');
INSERT INTO utilisateur (`id`, `email`, `motdepasse`, `nom`, `prenom`, `sso`, `active`, `link`,`langue`) VALUES ('1', 'john.doe@toto.fr', '$2a$10$D9vw/TkRDK8b6qhLYalokOPaRm/lhMVqTqheenlQGx86ITo3lt7/G', 'John', 'Doe', 'john.doe','1',null,'en'), ('2', 'admin@admintoto.fr', '$2a$10$D9vw/TkRDK8b6qhLYalokOPaRm/lhMVqTqheenlQGx86ITo3lt7/G', 'Admin', 'Admin', 'admin.admin','1',null,'fr');
/**admin.admin jwtpass      john.doe jwtpass*/
INSERT INTO utilisateur_profil (`utilisateur_id`, `profil_id`) VALUES ('1', '1'),('2', '1'),('2', '2');