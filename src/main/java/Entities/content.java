package Entities;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class content {
    long id;
   private String nom;
    private String realisateur;
    private LocalDate annerdesortie;
    private String langue;
    private String paysorigine;
    private List<String> listegenre;
    private Image img;

    public content(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, Image img) {
        this.id = id;
        this.nom = nom;
        this.realisateur = realisateur;
        this.annerdesortie = annerdesortie;
        this.langue = langue;
        this.paysorigine = paysorigine;
        this.listegenre = listegenre;
        this.img = img;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        content content = (content) o;
        return id == content.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
