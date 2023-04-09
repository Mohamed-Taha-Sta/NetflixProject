package Entities;

import javafx.scene.image.Image;

import java.io.File;
import java.time.LocalDate;

public class Episode {

    private long ID;
    private long SeasonParentID;
    private String Name;
    private int Number;
    private LocalDate DebutDate;
    private LocalDate PremiereDate;
    private File image;
    private Resume resume;
    private File media;

    private long VueNbr;
    private long Score;
    private long Votes;

    @Override
    public String toString() {
        return "\nEpisode{" +
                "ID=" + ID +
                ", SeasonParentID=" + SeasonParentID +
                ", Name='" + Name + '\'' +
                ", Number=" + Number +
                ", DebutDate=" + DebutDate +
                ", PremiereDate=" + PremiereDate +
                ", VueNbr=" + VueNbr +
                ", Score=" + Score +
                ", Votes=" + Votes +
                '}';
    }

    public Episode(long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, File image, Resume resume, File media, long vueNbr, long score, long votes) {
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

    public Episode(long ID, long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, File image, long vueNbr, long score, long votes) {
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
    public Episode(long ID, long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, File image, long vueNbr, long score, long votes,File file) {
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
        media = file;
    }

    public Episode(long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, Resume resume, long vueNbr, long score, long votes, File file,File Image) {
        SeasonParentID = seasonParentID;
        Name = name;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.resume = resume;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
        media = file;
        image = Image;
    }

    public Episode(long ID, long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, File image, Resume resume, File media, long vueNbr, long score, long votes) {
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


    public Episode(long ID, long seasonParentID, int number, LocalDate debutDate, LocalDate premiereDate, File image, Resume resume, File media, long vueNbr, long score, long votes) {
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

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
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

    public File getMedia() {
        return media;
    }

    public void setMedia(File media) {
        this.media = media;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
