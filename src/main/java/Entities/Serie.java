package Entities;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Serie extends Content{
    private long SeasonNumber;

    private List<Season> seasonList;

    public Serie(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, Image img) {
        super(id, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
    }

    public Serie(long id, String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, Image img, long SeasonNumber) {
        super(id, nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        this.SeasonNumber=SeasonNumber;
    }

    public Serie(String nom, String realisateur, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, Image img, long seasonNumber) {
        super(nom, realisateur, annerdesortie, langue, paysorigine, listegenre, img);
        SeasonNumber = seasonNumber;
    }

    public long getSeasonNumber() {
        return SeasonNumber;
    }

    public void setSeasonNumber(long seasonNumber) {
        SeasonNumber = seasonNumber;
    }

    public void addSeason(Season season){
        seasonList.add(season);
    }

}
