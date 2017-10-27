package fr.quintipio.javarestangulararchi.model;

import fr.quintipio.javarestangulararchi.configuration.Constantes;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="UTILISATEUR")
public class Utilisateur extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    @Pattern(regexp = Constantes.LOGIN_REGEX)
    String sso;

    @JsonIgnore
    @Column(name="linkActivation", unique=true)
    String linkActivation;

    @JsonIgnore
    @Column(name="linkResetPassword", unique=true)
    String linkResetPassword;

    @JsonIgnore
    @NotNull
    @Column(name="active",nullable = false)
    Boolean isActive;

    @NotEmpty
    @NotNull
    @Column(name="email",nullable = false)
    @Size(max = 100, min = 5)
    @Email
    String email;

    @NotEmpty
    @Column(name="motdepasse", nullable=false)
    @NotNull
    @Size(max = 100, min = 6)
    String motDePasse;

    @NotEmpty
    @Column(name="prenom", nullable=false)
    @NotNull
    @Size(min = 2, max = 100)
    private String prenom;


    @NotEmpty
    @Column(name="nom", nullable=false)
    @NotNull
    @Size(min = 2, max = 100)
    private String nom;

    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UTILISATEUR_PROFIL",
            joinColumns = { @JoinColumn(name = "UTILISATEUR_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PROFIL_ID") })
    Set<Profil> userProfiles = new HashSet<>();

    @NotNull
    @NotEmpty
    @Size(min=2, max = 6)
    @Column(name = "langue", nullable = false)
    private String langue;


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


    public String getLinkActivation() {
        return linkActivation;
    }

    public void setLinkActivation(String linkActivation) {
        this.linkActivation = linkActivation;
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }


    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }


    public String getLinkResetPassword() {
        return linkResetPassword;
    }

    public void setLinkResetPassword(String linkResetPassword) {
        this.linkResetPassword = linkResetPassword;
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
                "id=" + id +
                ", sso='" + sso + '\'' +
                ", linkActivation='" + linkActivation + '\'' +
                ", linkResetPassword='" + linkResetPassword + '\'' +
                ", isActive=" + isActive +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", userProfiles=" + userProfiles +
                ", langue='" + langue + '\'' +
                '}';
    }
}
