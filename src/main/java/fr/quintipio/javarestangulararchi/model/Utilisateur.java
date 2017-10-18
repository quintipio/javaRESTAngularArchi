package fr.quintipio.javarestangulararchi.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="UTILISATEUR")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull
    @NotEmpty
    @Column(name="sso", unique=true, nullable=false)
    String sso;

    @NotEmpty
    @NotNull
    @Column(name="email",nullable = false)
    @Size(max = 255)
    @Length(max = 255)
    @Email
    String email;

    @NotEmpty
    @Column(name="motdepasse", nullable=false)
    @NotNull
    @Size(min= 8 ,max = 255)
    @Length(min= 8 ,max = 255)
    String motDePasse;

    @NotEmpty
    @Column(name="prenom", nullable=false)
    @NotNull
    @Size(max = 255)
    @Length(max = 255)
    private String prenom;


    @NotEmpty
    @Column(name="nom", nullable=false)
    @NotNull
    @Length(max = 255)
    @Size(max = 255)
    private String nom;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UTILISATEUR_PROFIL",
            joinColumns = { @JoinColumn(name = "UTILISATEUR_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PROFIL_ID") })
    Set<Profil> userProfiles = new HashSet<>();


    public Utilisateur() {

    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSso() {
        return sso;
    }

    public void setSso(String sso) {
        this.sso = sso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Profil> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<Profil> userProfiles) {
        this.userProfiles = userProfiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

    Utilisateur that = (Utilisateur) o;

        if (!id.equals(that.id)) return false;
        return sso.equals(that.sso);
}

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sso.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "sso='" + sso + '\'' +
                '}';
    }
}
