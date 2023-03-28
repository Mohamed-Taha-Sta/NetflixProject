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

    public Episode(long ID, int number, LocalDate debutDate, LocalDate premiereDate, Image image, Resume resume) {
        this.ID = ID;
        Number = number;
        DebutDate = debutDate;
        PremiereDate = premiereDate;
        this.image = image;
        this.resume = resume;
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
}
