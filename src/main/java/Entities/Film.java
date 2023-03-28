package Entities;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Film extends Content{
private LocalTime duree;

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
