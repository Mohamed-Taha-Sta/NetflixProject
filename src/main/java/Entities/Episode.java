package Entities;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.PrimitiveIterator;

public class Episode {

    private long ID;
    private int Number;
    private LocalDate DebutDate;
    private LocalDate PremiereDate;
    private Image image;
    private Resume resume;

    private long VueNbr;
    private long Score;
    private long Votes;


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
}
