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
    private long id_realisateur;


    public long getId_realisateur() {
        return id_realisateur;
    }

    public void setId_realisateur(long id_realisateur) {
        this.id_realisateur = id_realisateur;
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


    //Constructeur Jesser
    public Film(Long id, String nom, String desription, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree,Long vue , Long score, Long vote ,  File synopsis, File film,Long idrealisateur) {
        super(id, nom, desription, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        VueNbr = vue;
        Score = score;
        Votes = vote;
        this.synopsis = synopsis;
        this.film = film;
        this.id_realisateur=idrealisateur;
    }
    public Film(String nom, String desription, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, ArrayList<Actor> acteur, File synopsis, File film,Long idrealisateur) {
        super(nom, desription, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        this.acteur = acteur;
        VueNbr = 0;
        Score = 0;
        Votes = 0;
        this.synopsis = synopsis;
        this.film = film;
        this.id_realisateur=idrealisateur;
    }
    public Film (long id)
    {
        super(id);
    }
   /* public ArrayList<String> getGenre() {
        return genre;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }*/


    public Film(String nom, String desc, LocalDate annerdesortie, String langue, String paysorigine,
                List<String> listegenre, File img, String duree, long vueNbr, long score, long votes,
                File synopsis, File film,Long idrealisateur) {
        super(nom, desc, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
        this.synopsis = synopsis;
        this.film = film;
        this.id_realisateur=idrealisateur;
    }

    //Constructor mta3 Taha
    public Film(String nom, String desc, LocalDate annerdesortie, String langue, String paysorigine,
                List<String> listegenre, File img, String duree,
                File synopsis, File film,Long idrealisateur,ArrayList<Actor> actorArrayList) {
        super(nom, desc, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        VueNbr = 0;
        Score = 0;
        Votes = 0;
        this.acteur = actorArrayList;
        this.synopsis = synopsis;
        this.film = film;
        this.id_realisateur=idrealisateur;
    }

    public Film(long id, String nom, String desc, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, String duree, ArrayList<Actor> acteur, long vueNbr, long score, long votes, File synopsis, File film,Long idrealisateur) {
        super(id, nom, desc, annerdesortie, langue, paysorigine, listegenre, img);
        this.duree = duree;
        this.acteur = acteur;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
        this.synopsis = synopsis;
        this.film = film;
        this.id_realisateur=idrealisateur;
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
                ", resume='" + id_realisateur + '\'' +
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
