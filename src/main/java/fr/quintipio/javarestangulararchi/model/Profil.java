package fr.quintipio.javarestangulararchi.model;

import javax.persistence.*;

@Entity
@Table(name="PROFIL")
public class Profil {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @Column(name="libelle", length=30, unique=true, nullable=false)
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
                "libelle='" + libelle + '\'' +
                '}';
    }
}
