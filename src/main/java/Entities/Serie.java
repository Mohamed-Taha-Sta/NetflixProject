package Entities;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.List;

public class Serie extends Content{
    long SeasonNumber;


    public Serie(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, Image img,long SeasonNumber) {
        super(id, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.SeasonNumber=SeasonNumber;
    }

    public long getSeasonNumber() {
        return SeasonNumber;
    }

    public void setSeasonNumber(long seasonNumber) {
        SeasonNumber = seasonNumber;
    }

}
