package Entities;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Film extends Content{
private String duree;
private ArrayList<Actor>acteur;

//private ArrayList<String>genre;
    private long VueNbr;
    private long Score;
    private long Votes;
    private File synopsis;
    private File film;
    private String resume;


    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

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

    public Film(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, ArrayList<Actor> acteur, File synopsis, File film,String resume) {
        super(nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        this.acteur = acteur;
        VueNbr = 0;
        Score = 0;
        Votes = 0;
        this.synopsis = synopsis;
        this.film = film;
        this.resume=resume;
    }

   /* public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }*/


    public Film(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, long vueNbr, long score, long votes, File synopsis, File film,String resume) {
        super(nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
        this.synopsis = synopsis;
        this.film = film;
        this.resume=resume;
    }

    public Film(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, ArrayList<Actor> acteur, long vueNbr, long score, long votes, File synopsis, File film,String resume) {
        super(id, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        this.acteur = acteur;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
        this.synopsis = synopsis;
        this.film = film;
        this.resume=resume;
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
                ", resume='" + resume + '\'' +
                ", id=" + id +
                ", nom='" + nom + '\'' +
                ", realisateur='" + Description + '\'' +
                ", annerdesortie=" + annerdesortie +
                ", langue='" + langue + '\'' +
                ", paysorigine='" + paysorigine + '\'' +
                ", listegenre=" + listegenre +
                ", img=" + img +
                '}';
    }
}
