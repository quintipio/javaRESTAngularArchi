INSERT INTO profil (`id`, `libelle`) VALUES ('1', 'STANDARD_USER'), ('2', 'ADMIN_USER');
INSERT INTO UTILISATEUR (ID, CREATED_DATE,LAST_MODIFIED_DATE,VERSION,EMAIL,ACTIVE,LANGUE,LINK_ACTIVATION, LINK_RESET_PASSWORD,MOTDEPASSE,NOM, PRENOM,SSO) VALUES
  (1,'2017-01-01 00:00:00',null,1,'superadmin@superadmin.fr',TRUE,'fr',null,null,'$2a$10$D9vw/TkRDK8b6qhLYalokOPaRm/lhMVqTqheenlQGx86ITo3lt7/G','admin','admin','admin.admin');
INSERT INTO utilisateur_profil (`utilisateur_id`, `profil_id`) VALUES ('1', '1'),('1', '2');