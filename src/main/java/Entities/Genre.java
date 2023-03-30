package Entities;

import javafx.scene.control.CheckBox;

public class Genre {
    protected String nom;
   protected CheckBox select;

    public Genre(String nom) {
        this.nom = nom;
        this.select=new CheckBox();
    }

    public String getNom() {
        return nom;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "nom='" + nom + '\'' +
                '}';
    }
}
