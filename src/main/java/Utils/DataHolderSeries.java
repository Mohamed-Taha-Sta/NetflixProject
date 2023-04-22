package Utils;

import Entities.Actor;
import Entities.Serie;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class DataHolderSeries {


    private static long IDSerie;
    private static String SeriesName;
    private static String Language;
    private static String Description;
    private static LocalDate DebutDate;
    private static String CountryOfOrigin;
    private static File Thumbnail;
    private static File Synopsis;
    private static List<String> GenreList;
    private static List<Long> MainActorsList;
    private static List<Long> SuppActorsList;
    private static Serie selectedSeries;
    private static ObservableList<Serie> series;

    public static ObservableList<Serie> getSeries() {
        return series;
    }

    public static void setSeries(ObservableList<Serie> series) {
        DataHolderSeries.series = series;
    }

    public static Serie getSelectedSeries() {
        return selectedSeries;
    }

    public static void setSelectedSeries(Serie selectedSeries) {
        DataHolderSeries.selectedSeries = selectedSeries;
    }

    public static LocalDate getDebutDate() {
        return DebutDate;
    }

    public static void setDebutDate(LocalDate debutDate) {
        DebutDate = debutDate;
    }

    public static List<Long> getMainActorsList() {
        return MainActorsList;
    }

    public static void setMainActorsList(List<Long> mainActorsList) {
        MainActorsList = mainActorsList;
    }

    public static List<Long> getSuppActorsList() {
        return SuppActorsList;
    }

    public static void setSuppActorsList(List<Long> suppActorsList) {
        SuppActorsList = suppActorsList;
    }

    public static String getSeriesName() {
        return SeriesName;
    }

    public static void setSeriesName(String seriesName) {
        SeriesName = seriesName;
    }

    public static String getLanguage() {
        return Language;
    }

    public static void setLanguage(String language) {
        Language = language;
    }

    public static String getCountryOfOrigin() {
        return CountryOfOrigin;
    }

    public static void setCountryOfOrigin(String countryOfOrigin) {
        CountryOfOrigin = countryOfOrigin;
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

    public static List<String> getGenreList() {
        return GenreList;
    }

    public static void setGenreList(List<String> genreList) {
        GenreList = genreList;
    }

    public static String getDescription() {
        return Description;
    }

    public static void setDescription(String description) {
        Description = description;
    }

    public static long getIDSerie() {
        return IDSerie;
    }

    public static void setIDSerie(long IDSerie) {
        DataHolderSeries.IDSerie = IDSerie;
    }

}
