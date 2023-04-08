package Entities;

import Utils.ConxDB;
import javafx.scene.image.Image;

import java.io.File;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Film extends Content{
private String duree;
private ArrayList<Actor>acteur;

//private ArrayList<String>genre;
    private long VueNbr;
    private long Score;
    private long Votes;
    private File synopsis;
    private File film;





    public long getVueNbr() {
        return VueNbr;
    }

    public void setVueNbr(long vueNbr) {
        VueNbr = vueNbr;
    }

    public long getScore() {
        return Score;
    }

    public void setScore(long score) {
        Score = score;
    }

    public long getVotes() {
        return Votes;
    }

    public void setVotes(long votes) {
        Votes = votes;
    }

    public Film(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, ArrayList<Actor> acteur, long vueNbr, long score, long votes, File synopsis, File film) {
        super(nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        this.acteur = acteur;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
        this.synopsis = synopsis;
        this.film = film;
    }

   /* public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }*/


    public Film(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, long vueNbr, long score, long votes, File synopsis, File film) {
        super(nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
        this.synopsis = synopsis;
        this.film = film;
    }

    public Film(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, ArrayList<Actor> acteur, long vueNbr, long score, long votes, File synopsis, File film) {
        super(id, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        this.acteur = acteur;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
        this.synopsis = synopsis;
        this.film = film;
    }

    public ArrayList<Actor> getActeur() {
        return acteur;
    }

    public void setActeur(ArrayList<Actor> acteur) {
        this.acteur = acteur;
    }

    public Film() {
    }

    public File getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(File synopsis) {
        this.synopsis = synopsis;
    }

    public File getFilm() {
        return film;
    }

    public void setFilm(File film) {
        this.film = film;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Film{" +
                "duree='" + duree + '\'' +
                ", acteur=" + acteur +
                ", VueNbr=" + VueNbr +
                ", Score=" + Score +
                ", Votes=" + Votes +
                ", synopsis=" + synopsis +
                ", film=" + film +
                ", nom='" + nom + '\'' +
                ", realisateur='" + realisateur + '\'' +
                ", annerdesortie=" + annerdesortie +
                ", langue='" + langue + '\'' +
                ", paysorigine='" + paysorigine + '\'' +
                ", listegenre=" + listegenre +
                ", img=" + img +
                '}';
    }
}
