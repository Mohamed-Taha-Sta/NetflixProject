package Entities;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class Serie extends Content{
    private long SeasonNumber;
    private long ID_PROD;
    private File synopsis;
    private List<Season> seasonList;
    private List<Actor> actorList;
    private List<Long> IDMainactorList;
    private List<Long> IDSuppactorList;

    public Serie(long id,long ID_PROD, String nom, String Description, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, long seasonNumber, File synopsis, List<Season> seasonList, List<Actor> actorList) {
        super(id, nom, Description, annerdesortie, langue, paysorigine, listegenre, img);
        SeasonNumber = seasonNumber;
        this.synopsis = synopsis;
        this.ID_PROD = ID_PROD;
        this.seasonList = seasonList;
        this.actorList = actorList;
    }

    public Serie(String nom,long ID_PROD, String Description, LocalDate annerdesortie, String langue, String paysorigine, List<String> listegenre, File img, File synopsis, List<Long> IDMainactorList,List<Long> IDSuppactorList) {
        super(nom, Description, annerdesortie, langue, paysorigine, listegenre, img);
        this.synopsis = synopsis;
        this.ID_PROD = ID_PROD;
        this.IDMainactorList = IDMainactorList;
        this.IDSuppactorList = IDSuppactorList;
    }

    public long getSeasonNumber() {
        return SeasonNumber;
    }

    public List<Long> getIDMainactorList() {
        return IDMainactorList;
    }

    public void setIDMainactorList(List<Long> IDMainactorList) {
        this.IDMainactorList = IDMainactorList;
    }


    public List<Long> getIDSuppactorList() {
        return IDSuppactorList;
    }

    public void setIDSuppactorList(List<Long> IDSuppactorList) {
        this.IDSuppactorList = IDSuppactorList;
    }

    public void setSeasonNumber(long seasonNumber) {
        SeasonNumber = seasonNumber;
    }

    public void addSeason(Season season){
        seasonList.add(season);
    }

    public List<Season> getSeasonList() {
        return seasonList;
    }

    public void setSeasonList(List<Season> seasonList) {
        this.seasonList = seasonList;
    }
    public File getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(File synopsis) {
        this.synopsis = synopsis;
    }

    public List<Actor> getActorList() {
        return actorList;
    }

    public void setActorList(List<Actor> actorList) {
        this.actorList = actorList;
    }

    public long getID_PROD() {
        return ID_PROD;
    }

    public void setID_PROD(long ID_PROD) {
        this.ID_PROD = ID_PROD;
    }


    public Serie() {
    }

    @Override
    public String toString() {
        return "Serie{" +
                "actorList=" + actorList + "\n" +
                ", id=" + id + "\n" +
                ", nom='" + nom + '\'' + "\n" +
                ", realisateur='" + Description + '\'' + "\n" +
                ", annerdesortie=" + annerdesortie + "\n" +
                ", langue='" + langue + '\'' + "\n" +
                ", paysorigine='" + paysorigine + '\'' + "\n" +
                ", listegenre=" + listegenre + "\n" +
                '}';
    }
}
