package fr.quintipio.javarestangulararchi.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="PROFIL")
public class Profil {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name="libelle", length=100,  unique=true, nullable=false)
    @Size(min = 2, max = 100,message = "erreur taille libelle")
    @NotEmpty(message = "erreur libelle vide")
    @NotNull(message = "erreur libelle null")
    String libelle;

    public Profil() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Profil profil = (Profil) o;

        return id.equals(profil.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Profil{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
