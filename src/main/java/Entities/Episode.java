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
    private File Synopsis;
    private String Description;
    private File media;

    private long VueNbr;
    private long Score;
    private long Votes;

    public Episode(long id, String epsisodeName, long seasonId) {
        this.ID = id;
        this.Name = epsisodeName;
        this.SeasonParentID = seasonId;
    }

    public Episode(Long id, long seasonID, String description, String episodeName, int episodeNumber,
                   LocalDate DebDate, LocalDate premDate, File fileImage, File fileSynopsis, File file) {
        this.ID = id;
        SeasonParentID = seasonID;
        this.Description = description;
        Name = episodeName;
        Number = episodeNumber;
        DebutDate = DebDate;
        this.PremiereDate = premDate;
        this.image = fileImage;
        this.Synopsis = fileSynopsis;
        this.media = file;
    }

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

    public Episode(long ID, long seasonParentID, String name, LocalDate debutDate,LocalDate premiereDate , File image) {
        this.ID = ID;
        SeasonParentID = seasonParentID;
        Name = name;
        DebutDate = debutDate;
        this.PremiereDate = premiereDate;
        this.image = image;
    }

    public Episode(long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, File image, File Synopsis, File media, long vueNbr, long score, long votes) {
        SeasonParentID = seasonParentID;
        Name = name;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        this.Synopsis = Synopsis;
        this.media = media;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
    }

    public Episode(long ID, long seasonParentID, String name, int number, LocalDate debutDate, LocalDate premiereDate, File image) {
        this.ID = ID;
        SeasonParentID = seasonParentID;
        Name = name;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
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

    public Episode(long seasonParentID, String name, String Description, LocalDate debutDate, LocalDate premiereDate, File Synopsis, File file, File Image) {
        SeasonParentID = seasonParentID;
        Name = name;
        this.Description = Description;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.Synopsis = Synopsis;
        VueNbr = 0;
        Score = 0;
        Votes = 0;
        media = file;
        image = Image;
    }

    public Episode(long ID, long seasonParentID, String Description,String name, int number, LocalDate debutDate, LocalDate premiereDate, File image, File Synopsis, File media, long vueNbr, long score, long votes) {
        this.ID = ID;
        this.Description = Description;
        SeasonParentID = seasonParentID;
        Name = name;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        this.Synopsis = Synopsis;
        this.media = media;
        VueNbr = vueNbr;
        Score = score;
        Votes = votes;
    }


    public Episode(long ID, long seasonParentID, int number, LocalDate debutDate, LocalDate premiereDate, File image, File Synopsis, File media, long vueNbr, long score, long votes) {
        this.ID = ID;
        SeasonParentID = seasonParentID;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        this.Synopsis = Synopsis;
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

    public File getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(File Synopsis) {
        this.Synopsis = Synopsis;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
