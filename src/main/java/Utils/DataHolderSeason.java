package Utils;

import Entities.Season;
import javafx.scene.control.DatePicker;

import java.io.File;
import java.time.LocalDate;

public class DataHolderSeason {

    private static long IDSeason;
    private static String Name;

    private static Season selectedSeason;
    private static String Description;
    private static String PreviousPage;
    private static LocalDate DebutDate;
    private static File Thumbnail;
    private static File Synopsis;

    public static String getName() {return Name;}

    public static void setName(String name) {
        Name = name;
    }

    public static File getThumbnail() {
        return Thumbnail;
    }

    public static void setThumbnail(File thumbnail) {
        Thumbnail = thumbnail;
    }

    public static File getSynopsis() {
        return Synopsis;
    }

    public static void setSynopsis(File synopsis) {
        Synopsis = synopsis;
    }

    public static String getDescription() {
        return Description;
    }

    public static void setDescription(String description) {
        Description = description;
    }

    public static LocalDate getDebutDate() {
        return DebutDate;
    }

    public static void setDebutDate(LocalDate debutDate) {
        DebutDate = debutDate;
    }

    public static long getIDSeason() {
        return IDSeason;
    }

    public static void setIDSeason(long IDSeason) {
        DataHolderSeason.IDSeason = IDSeason;
    }

    public static String getPreviousPage() {
        return PreviousPage;
    }

    public static void setPreviousPage(String previousPage) {
        PreviousPage = previousPage;
    }

    public static Season getSelectedSeason() {
        return selectedSeason;
    }

    public static void setSelectedSeason(Season selectedSeason) {
        DataHolderSeason.selectedSeason = selectedSeason;
    }



}
