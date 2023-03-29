package Entities;

import javafx.scene.image.Image;

import java.time.LocalDate;
import javafx.scene.media.*;
import java.util.PrimitiveIterator;

public class Episode {

    private long ID;
    private long SeasonParentID;
    private String Name;
    private int Number;
    private LocalDate DebutDate;
    private LocalDate PremiereDate;
    private Image image;
    private Resume resume;
    private Media media;

    private long VueNbr;
    private long Score;
    private long Votes;


    public Episode(long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, Image image, Resume resume, Media media, long vueNbr, long score, long votes) {
        SeasonParentID = seasonParentID;
        Name = name;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        this.resume = resume;
        this.media = media;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
    }

    public Episode(long ID, long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, Image image, long vueNbr, long score, long votes) {
        this.ID = ID;
        SeasonParentID = seasonParentID;
        Name = name;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
    }

    public Episode(long ID, long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, Image image, Resume resume, Media media, long vueNbr, long score, long votes) {
        this.ID = ID;
        SeasonParentID = seasonParentID;
        Name = name;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        this.resume = resume;
        this.media = media;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Episode(long ID, long seasonParentID, int number, LocalDate debutDate, LocalDate premiereDate, Image image, Resume resume, Media media, long vueNbr, long score, long votes) {
        this.ID = ID;
        SeasonParentID = seasonParentID;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        this.resume = resume;
        this.media = media;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
    }

    public Episode(long ID, int number, LocalDate debutDate, LocalDate premiereDate, Image image, Resume resume, long vueNbr, long score, long votes) {
        this.ID = ID;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        this.resume = resume;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
    }

    public Episode() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public LocalDate getDebutDate() {
        return DebutDate;
    }

    public void setDebutDate(LocalDate debutDate) {
        DebutDate = debutDate;
    }

    public LocalDate getPremiereDate() {
        return PremiereDate;
    }

    public void setPremiereDate(LocalDate premiereDate) {
        PremiereDate = premiereDate;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
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

    public long getSeasonParentID() {
        return SeasonParentID;
    }

    public void setSeasonParentID(long seasonParentID) {
        SeasonParentID = seasonParentID;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }
}
