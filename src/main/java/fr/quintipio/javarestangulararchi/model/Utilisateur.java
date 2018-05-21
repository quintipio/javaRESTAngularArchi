package fr.quintipio.javarestangulararchi.model;

import fr.quintipio.javarestangulararchi.configuration.Constantes;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="UTILISATEUR")
public class Utilisateur extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotNull(message = "erreur sso null")
    @NotEmpty(message = "erreur sso vide")
    @Size(min = 3, max = 100,message = "erreur taille sso")
    @Pattern(regexp = Constantes.LOGIN_REGEX,message ="erreur pattern sso")
    String sso;

    @JsonIgnore
    @Column(name="linkActivation", unique=true)
    String linkActivation;

    @JsonIgnore
    @Column(name="linkResetPassword", unique=true)
    String linkResetPassword;

    @Column(name="active",nullable = false)
    Boolean actif;

    @NotEmpty(message = "erreur email vide")
    @NotNull(message = "erreur email null")
    @Column(name="email",nullable = false)
    @Size(max = 100, min = 5,message = "erreur taille email")
    @Email(message = "erreur pattern email")
    String email;

    @NotEmpty(message = "erreur mot de passe vide")
    @Column(name="motdepasse", nullable=false)
    @NotNull(message = "erreur mot de passe null")
    @Size(max = 100, min = 6, message = "Erreur taille mot de passe")
    String motDePasse;

    @NotEmpty(message = "erreur prenom vide")
    @Column(name="prenom", nullable=false)
    @NotNull(message = "erreur prenom null")
    @Size(min = 2, max = 100,message = "erreur taille prenom")
    private String prenom;


    @NotEmpty(message = "erreur nom vide")
    @Column(name="nom", nullable=false)
    @NotNull(message = "erreur nom null")
    @Size(min = 2, max = 100,message = "erreur taille nom")
    private String nom;

    @NotEmpty(message = "erreur profils vide")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UTILISATEUR_PROFIL",
            joinColumns = { @JoinColumn(name = "UTILISATEUR_ID") },
            inverseJoinColumns = { @JoinColumn(name = "PROFIL_ID") })
    Set<Profil> userProfiles = new HashSet<>();

    @NotNull(message = "erreur langue null")
    @NotEmpty(message = "erreur langue vide")
    @Size(min=2, max = 6,message = "erreur taille langue")
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

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
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
                ", actif=" + actif +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", userProfiles=" + userProfiles +
                ", langue='" + langue + '\'' +
                '}';
    }
}
