package Entities;

import Utils.ConxDB;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Film extends Content{
private LocalTime duree;
private ArrayList<Actor>acteur;
private File file;

    public Film(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, Image img, LocalTime duree, ArrayList<Actor> acteur, File file) {
        super(id, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        this.acteur = acteur;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Film(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, Image img, LocalTime duree, ArrayList<Actor> acteur) {
        super(id, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        this.acteur = acteur;
    }

    public ArrayList<Actor> getActeur() {
        return acteur;
    }

    public void setActeur(ArrayList<Actor> acteur) {
        this.acteur = acteur;
    }

    public Film() {
    }

    public Film(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, Image img, LocalTime duree) {
        super(id, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
    }

    public LocalTime getDuree() {
        return duree;
    }

    public void setDuree(LocalTime duree) {
        this.duree = duree;
    }



























}
