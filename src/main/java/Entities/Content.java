package Entities;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class Content {
    long id;
   protected String nom;
    protected String Description;
    protected LocalDate annerdesortie;
    protected String langue;
    protected String paysorigine;
    protected List<String> listegenre;
    protected File img;

    public Content() {
    }

    public Content(long id, String nom, String Description, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img) {
        this.id = id;
        this.nom = nom;
        this.Description = Description;
        this.annerdesortie = annerdesortie;
        this.langue = langue;
        this.paysorigine = paysorigine;
        this.listegenre = listegenre;
        this.img = img;
    }

    public Content(String nom, String Description, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img) {
        this.nom = nom;
        this.Description = Description;
        this.annerdesortie = annerdesortie;
        this.langue = langue;
        this.paysorigine = paysorigine;
        this.listegenre = listegenre;
        this.img = img;
    }

    public Content(String nom, String Description, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre) {
        this.nom = nom;
        this.Description = Description;
        this.annerdesortie = annerdesortie;
        this.langue = langue;
        this.paysorigine = paysorigine;
        this.listegenre = listegenre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return id == content.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public LocalDate getAnnerdesortie() {
        return annerdesortie;
    }

    public void setAnnerdesortie(LocalDate annerdesortie) {
        this.annerdesortie = annerdesortie;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public String getPaysorigine() {
        return paysorigine;
    }

    public void setPaysorigine(String paysorigine) {
        this.paysorigine = paysorigine;
    }

    public List<String> getListegenre() {
        return listegenre;
    }

    public void setListegenre(List<String> listegenre) {
        this.listegenre = listegenre;
    }

    public File getImg() {
        return img;
    }

    public void setImg(File img) {
        this.img = img;
    }
}
