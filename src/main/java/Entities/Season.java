package Entities;

import javafx.scene.image.Image;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Season {

    private long ID;
    private String name;
    private String Description;
    private File Synopsis;
    private long SERIE_ID;
    private int Number;
    private LocalDate DebutDate;
    private File Thumbnail;
    List<Episode> episodeList;


    public Season(long ID, File synopsis, long SERIE_ID, int number,
                  LocalDate debutDate, File thumbnail, List<Episode> episodeList) {
        this.ID = ID;
        Synopsis = synopsis;
        this.SERIE_ID = SERIE_ID;
        Number = number;
        DebutDate = debutDate;
        Thumbnail = thumbnail;
        this.episodeList = episodeList;
    }
    public Season(long ID, String Name,File synopsis, long SERIE_ID, int number,
                  LocalDate debutDate, File thumbnail) {
        this.ID = ID;
        Synopsis = synopsis;
        this.name = Name;
        this.SERIE_ID = SERIE_ID;
        Number = number;
        DebutDate = debutDate;
        Thumbnail = thumbnail;
    }

    public Season(long ID,String name,String Description, File synopsis, long SERIE_ID, int number,
                  LocalDate debutDate, File thumbnail) {
        this.name=name;
        this.ID = ID;
        this.Description = Description;
        Synopsis = synopsis;
        this.SERIE_ID = SERIE_ID;
        Number = number;
        DebutDate = debutDate;
        Thumbnail = thumbnail;
        this.episodeList = episodeList;
    }

    public Season(String name, File synopsis,String Description, long SERIE_ID, int number, LocalDate debutDate, File thumbnail) {

        this.name = name;
        this.Description = Description;
        Synopsis = synopsis;
        this.SERIE_ID = SERIE_ID;
        Number = number;
        DebutDate = debutDate;
        Thumbnail = thumbnail;
    }

    public Season(long SERIE_ID, int number, LocalDate debutDate, File thumbnail) {
        this.SERIE_ID = SERIE_ID;
        Number = number;
        DebutDate = debutDate;
        Thumbnail = thumbnail;
    }

    public Season(long SERIE_ID,String Description,String Name , LocalDate debutDate, File thumbnail,File Synopsis) {
        this.SERIE_ID = SERIE_ID;
        this.Description = Description;
        this.name = Name;
        DebutDate = debutDate;
        Thumbnail = thumbnail;
        this.Synopsis = Synopsis;
    }

    public Season(File synopsis, long SERIE_ID, int number, LocalDate debutDate, File thumbnail) {
        Synopsis = synopsis;
        this.SERIE_ID = SERIE_ID;
        Number = number;
        DebutDate = debutDate;
        Thumbnail = thumbnail;
        this.episodeList = episodeList;
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

    public File getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(File thumbnail) {
        Thumbnail = thumbnail;
    }


    public File getSynopsis() {
        return Synopsis;
    }

    public void setSynopsis(File synopsis) {
        Synopsis = synopsis;
    }

    public long getSERIE_ID() {
        return SERIE_ID;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSERIE_ID(long SERIE_ID) {
        this.SERIE_ID = SERIE_ID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    @Override
    public String toString() {
        return "Season{" +
                "ID=" + ID +
                ", Number=" + Number +
                ", DebutDate=" + DebutDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Season season)) return false;
        return getID() == season.getID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID());
    }

    public void add_Episode(Episode episode)
    {
        episodeList.add(episode);
    }

    public List<Episode> getEpisodeList() {
        return episodeList;
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
    }


}
